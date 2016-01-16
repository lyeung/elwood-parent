/*
 *
 *  Copyright (C) 2015-2016 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.builder.command.ProcessBuilderCommandFactory;
import org.lyeung.elwood.builder.command.ProjectBuilderCommandFactory;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.builder.model.ProjectModel;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.command.MkDirCommandFactory;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.command.WriteFileCommandFactory;
import org.lyeung.elwood.common.command.WriteFileCommandParamBuilder;
import org.lyeung.elwood.common.command.event.impl.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.command.impl.ShellCommandExecutorImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandImpl;
import org.lyeung.elwood.common.event.Event;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.domain.ProjectKey;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandFactory;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParam;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParamBuilder;
import org.lyeung.elwood.executor.command.BuildJobCommand;
import org.lyeung.elwood.executor.command.BuildJobException;
import org.lyeung.elwood.executor.command.CheckoutDirCreatorCommandFactory;
import org.lyeung.elwood.executor.command.ElwoodLogFileCreatorCommandFactory;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandFactory;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandParamBuilder;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommandFactory;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandFactory;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.CloneCommandParamBuilder;
import org.lyeung.elwood.vcs.command.impl.GitCloneEventData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by lyeung on 6/08/2015.
 */
public class BuildJobCommandImpl implements BuildJobCommand {

    private static final String WORKSPACE_DIR = "/tmp/workspace";

    private final Param param;

    public BuildJobCommandImpl(Param param) {
        this.param = param;
    }

    @Override
    public Integer execute(KeyCountTuple keyCountTuple) {
        final String key = keyCountTuple.getKey();

        // TODO: move this into a command
        final Optional<Project> project = param.projectRepository.getOne(new ProjectKey(key));
        if (!project.isPresent()) {
            return null;
        }

        // TODO: move this into a command
        final Optional<Build> build = param.buildRepository.getOne(new BuildKey(key));

        final File targetDir = mkDir(build.get(), keyCountTuple);
        final File elwoodLog = createElwoodLog(targetDir);
        final File checkedOutDir = checkOutSource(
                keyCountTuple, project.get(), targetDir, elwoodLog);
        final Process process = createProcess(project.get(), build.get(), checkedOutDir);
        final Integer result = buildProject(keyCountTuple, process, elwoodLog);

        final Optional<BuildResult> buildResult = getBuildResult( keyCountTuple);
        if (!buildResult.isPresent()) {
            return null;
        }

        saveBuildResult(buildResult.get(), result);

        final BuildResultMavenStats mavenStats = readMavenStats(
                checkedOutDir, buildResult.get().getKey());
        saveMavenStats(mavenStats);

        final boolean removedFuture = param.buildMapLog.removeFuture(keyCountTuple);
        assert removedFuture;

        final boolean removedContent = param.buildMapLog.removeContent(keyCountTuple);
        assert removedContent;

        return result;
    }

    private Optional<BuildResult> getBuildResult(KeyCountTuple keyCountTuple) {
        final BuildResultKey buildResultKey = new BuildResultKey(new BuildKey(
                keyCountTuple.getKey()), keyCountTuple.getCount());
        return param.buildResultRepository.getOne(buildResultKey);
    }

    private void saveBuildResult(BuildResult buildResult, Integer result) {
        buildResult.setBuildStatus(getBuildStatus(result));
        buildResult.setFinishRunDate(new Date());
        param.buildResultRepository.save(buildResult);
    }


    private void saveMavenStats(BuildResultMavenStats mavenStats) {
        param.saveBuildResultMavenStatsCommandFactory.makeCommand().execute(mavenStats);
    }

    private BuildResultMavenStats readMavenStats(File checkedOutDir, BuildResultKey buildResultKey) {
        return param.getMavenStatusCommandFactory.makeCommand()
                .execute(new GetMavenStatusCommandParamBuilder()
                        .checkedOutDir(checkedOutDir)
                        .buildResultKey(buildResultKey)
                        .build());
    }

    private BuildStatus getBuildStatus(Integer result) {
        if (result != 0) {
            return BuildStatus.FAILED;
        }

        return BuildStatus.SUCCEEDED;
    }

    private File createElwoodLog(File targetDir) {
        return param.elwoodLogFileCreatorCommandFactory.makeCommand().execute(targetDir);
    }

    private File mkDir(Build build, KeyCountTuple keyCountTuple) {
        return param.mkDirCommandFactory.makeCommand()
                .execute(new MkDirCommandParamBuilder()
                        .directory(getTargetDirectory(build, keyCountTuple))
                        .build());
    }

    private File checkOutSource(
            KeyCountTuple keyCountTuple, Project project, File targetDir, File elwoodLog) {
        final CloneCommand cloneCommand = param.cloneCommandFactory.makeCommand(
                Collections.singletonList(new DefaultEventListener<>(
                        new GitCloneBuildMapWriter(
                                keyCountTuple, param.buildMapLog, elwoodLog))));

        final File checkedOutDir = param.checkoutDirCreatorCommandFactory
                .makeCommand().execute(targetDir);

        return cloneCommand.execute(new CloneCommandParamBuilder()
                .localDirectory(checkedOutDir.getAbsolutePath())
                .remoteUri(project.getSourceUrl())
                .authenticationType(CloneCommandParam
                        .AuthenticationType.valueOf(
                                project.getAuthenticationType()))
                .identityKey(project.getIdentityKey())
                .passphrase(project.getPassphrase())
                .build());
    }

    private void attachRunListener(Build build, File checkedOutDir) {
        final AttachRunListenerCommandParam attachRunListenerCommandParam =
                new AttachRunListenerCommandParamBuilder()
                        .build(build)
                        .outputDir(checkedOutDir)
                        .build();

        final String updatedPomContent = param.attachRunListenerCommandFactory.makeCommand()
                .execute(attachRunListenerCommandParam);
        final File updatedPom = new File(checkedOutDir, "pom.xml");

        final File updatedFile = param.writeFileCommandFactory.makeCommand()
                .execute(new WriteFileCommandParamBuilder()
                        .content(updatedPomContent)
                        .file(updatedPom)
                        .build());
        assert updatedFile != null;
    }

    private Process createProcess(Project project, Build build, File checkedOutDir) {
        attachRunListener(build, checkedOutDir);
        return param.processBuilderCommandFactory.makeCommand(new ShellCommandImpl()).execute(
                toBuildModel(project, build, checkedOutDir));
    }

    private Integer buildProject(KeyCountTuple keyCountTuple, Process process, File elwoodLog) {
        return param.projectBuilderCommandFactory.makeCommand(new ShellCommandExecutorImpl(
                Collections.singletonList(new DefaultEventListener<>(
                        new ShellCommandBuildMapWriter<>(keyCountTuple, Arrays.asList(
                                new FileConsumer(keyCountTuple, elwoodLog),
                                new MapConsumer(keyCountTuple, param.buildMapLog)))))))

                .execute(process);
    }

    private BuildModel toBuildModel(Project project, Build build,
        File clonedDirectory) {

        final BuildModel buildModel = new BuildModel();
        buildModel.setWorkingDirectory(clonedDirectory.getAbsolutePath());
        buildModel.setEnvironmentVars(build.getEnvironmentVars());
        buildModel.setBuildCommand(build.getBuildCommand());
        buildModel.setProjectModel(toProjectModel(project));

        return buildModel;
    }

    private ProjectModel toProjectModel(Project project) {
        final ProjectModel projectModel = new ProjectModel();
        projectModel.setName(project.getName());
        projectModel.setDescription(project.getDescription());
        projectModel.setBuildFile(project.getBuildFile());

        return projectModel;
    }

    private String getTargetDirectory(Build build, KeyCountTuple keyCountTuple) {
        return WORKSPACE_DIR + "/" + build.getWorkingDirectory() + "/"
                + keyCountTuple.toString();
    }

    private static class GitCloneBuildMapWriter<K extends Serializable>
            implements Consumer<Event<GitCloneEventData>> {

        private final K key;

        private final BuildMapLog buildMapLog;

        private final File file;

        public GitCloneBuildMapWriter(K key, BuildMapLog buildMapLog, File file) {
            this.key = key;
            this.buildMapLog = buildMapLog;
            this.file = file;
        }

        @Override
        public void accept(Event<GitCloneEventData> event) {
            final byte[] data = event.getEventData().getData();
            try {
                final String content = new String(data, EncodingConstants.UTF_8);

                // TODO: remove this soon
                System.out.println(content);

                write(content, file);
                buildMapLog.append(key, content);
            } catch (UnsupportedEncodingException e) {
                throw new BuildJobException(
                        "unable to clone source", e, key.toString());
            }
        }

        private void write(String content, File file) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(content);
            } catch (IOException e) {
                throw new BuildJobException(
                        "unable to write to file", e, key.toString());
            }
        }
    }

    private static class ShellCommandBuildMapWriter<K extends Serializable>
            implements Consumer<Event<ShellCommandExecutorEventData>> {

        private final K key;

        private final List<ShellCommandConsumer<K>> shellCommandConsumers;

        public ShellCommandBuildMapWriter(
                K key, List<ShellCommandConsumer<K>> shellCommandConsumers) {

            this.key = key;
            this.shellCommandConsumers = shellCommandConsumers;
        }

        @Override
        public void accept(Event<ShellCommandExecutorEventData> event) {
            final byte[] data = event.getEventData().getData();
            for (ShellCommandConsumer<K> shellCommandConsumer : shellCommandConsumers) {
                try {
                    shellCommandConsumer.consume(data);
                } catch (IOException e) {
                    throw new BuildJobException(
                            "io exception", e, key.toString());
                }
            }
        }
    }


    private interface ShellCommandConsumer<K> {

        void consume(byte[] data) throws IOException;
    }

    private static class MapConsumer<K extends Serializable> implements ShellCommandConsumer {

        private final K key;

        private final BuildMapLog<K> buildMapLog;

        public MapConsumer(K key, BuildMapLog<K> buildMapLog) {
            this.key = key;
            this.buildMapLog = buildMapLog;
        }

        @Override
        public void consume(byte[] data) throws IOException {
            final String content = new String(data, EncodingConstants.UTF_8);
            buildMapLog.append(key, content);
        }
    }

    private static class FileConsumer<K extends Serializable> implements ShellCommandConsumer {

        private final K key;

        private final File file;

        public FileConsumer(K key, File file) {
            this.key = key;
            this.file = file;
        }

        @Override
        public void consume(byte[] data) throws IOException {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(new String(data, EncodingConstants.UTF_8));
            } catch (IOException e) {
                throw new BuildJobException(
                        "unable to write to file", e, key.toString());
            }
        }
    }

    public static class Param {

        private ProjectRepository projectRepository;

        private BuildRepository buildRepository;

        private BuildResultRepository buildResultRepository;

        private SaveBuildResultMavenStatsCommandFactory saveBuildResultMavenStatsCommandFactory;

        private BuildMapLog<KeyCountTuple> buildMapLog;

        private MkDirCommandFactory mkDirCommandFactory;

        private CheckoutDirCreatorCommandFactory checkoutDirCreatorCommandFactory;

        private ElwoodLogFileCreatorCommandFactory elwoodLogFileCreatorCommandFactory;

        private CloneCommandFactory cloneCommandFactory;

        private AttachRunListenerCommandFactory attachRunListenerCommandFactory;

        private WriteFileCommandFactory writeFileCommandFactory;

        private ProcessBuilderCommandFactory processBuilderCommandFactory;

        private ProjectBuilderCommandFactory projectBuilderCommandFactory;

        private GetMavenStatusCommandFactory getMavenStatusCommandFactory;

        public Param projectRepository(ProjectRepository projectRepository) {
            this.projectRepository = projectRepository;
            return this;
        }

        public Param buildRepository(BuildRepository buildRepository) {
            this.buildRepository = buildRepository;
            return this;
        }

        public Param buildResultRepository(BuildResultRepository buildResultRepository) {
            this.buildResultRepository = buildResultRepository;
            return this;
        }

        public Param saveBuildResultMavenStatsCommandFactory(
                SaveBuildResultMavenStatsCommandFactory saveBuildResultMavenStatsCommandFactory) {
            this.saveBuildResultMavenStatsCommandFactory = saveBuildResultMavenStatsCommandFactory;
            return this;
        }

        public Param buildMapLog(BuildMapLog buildMapLog) {
            this.buildMapLog = buildMapLog;
            return this;
        }

        public Param mkDirCommandFactory(MkDirCommandFactory mkDirCommandFactory) {
            this.mkDirCommandFactory = mkDirCommandFactory;
            return this;
        }

        public Param checkOutDirCreatorCommandFactory(
                CheckoutDirCreatorCommandFactory checkoutDirCreatorCommandFactory) {
            this.checkoutDirCreatorCommandFactory = checkoutDirCreatorCommandFactory;
            return this;
        }

        public Param elwoodLogFileCreatorCommandFactory(
                ElwoodLogFileCreatorCommandFactory elwoodLogFileCreatorCommandFactory) {
            this.elwoodLogFileCreatorCommandFactory = elwoodLogFileCreatorCommandFactory;
            return this;
        }

        public Param cloneCommandFactory(CloneCommandFactory cloneCommandFactory) {
            this.cloneCommandFactory = cloneCommandFactory;
            return this;
        }

        public Param attachRunListenerCommandFactory(
                AttachRunListenerCommandFactory attachRunListenerCommandFactory) {
            this.attachRunListenerCommandFactory = attachRunListenerCommandFactory;
            return this;
        }

        public Param writeFileCommandFactory(WriteFileCommandFactory writeFileCommandFactory) {
            this.writeFileCommandFactory = writeFileCommandFactory;
            return this;
        }

        public Param processBuilderCommandFactory(
                ProcessBuilderCommandFactory processBuilderCommandFactory) {
            this.processBuilderCommandFactory = processBuilderCommandFactory;
            return this;
        }

        public Param projectBuilderCommandFactory(
                ProjectBuilderCommandFactory projectBuilderCommandFactory) {
            this.projectBuilderCommandFactory = projectBuilderCommandFactory;
            return this;
        }

        public Param getMavenStatusCommandFactory(GetMavenStatusCommandFactory factory) {
            this.getMavenStatusCommandFactory = factory;
            return this;
        }
    }
}
