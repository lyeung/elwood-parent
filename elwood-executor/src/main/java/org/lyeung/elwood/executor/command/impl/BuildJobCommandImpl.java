package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.builder.command.impl.ProcessBuilderCommandImpl;
import org.lyeung.elwood.builder.command.impl.ProjectBuilderCommandImpl;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.builder.model.ProjectModel;
import org.lyeung.elwood.common.command.MkDirCommand;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.command.event.impl.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.command.impl.MkDirCommandImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandExecutorImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandImpl;
import org.lyeung.elwood.common.event.Event;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.BuildJobCommand;
import org.lyeung.elwood.executor.command.BuildJobException;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.CloneCommandParamBuilder;
import org.lyeung.elwood.vcs.command.impl.GitCloneCommandImpl;
import org.lyeung.elwood.vcs.command.impl.GitCloneEventData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * Created by lyeung on 6/08/2015.
 */
public class BuildJobCommandImpl implements BuildJobCommand {

    private static final String WORKSPACE_DIR = "/tmp/workspace";
    private static final String ELWOOD_LOG = "elwood.log";

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    private final BuildMapLog buildMapLog;

    public BuildJobCommandImpl(ProjectRepository projectRepository,
                               BuildRepository buildRepository, BuildMapLog buildMapLog) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
        this.buildMapLog = buildMapLog;
    }

    @Override
    public Integer execute(String key) {
        final Project project = projectRepository.getOne(key);
        final Build build = buildRepository.getOne(key);

        final File targetDir = mkDir(build);
        final File elwoodLog = new File(targetDir, ELWOOD_LOG);
        final File checkedOutDir = checkOutSource(
                key, project, targetDir, elwoodLog);
        final Process process = createProcess(project, build, checkedOutDir);
        final Integer result = buildProject(key, process, elwoodLog);

        return result;
    }

    private File mkDir(Build build) {
        final MkDirCommand mkDirCommand = new MkDirCommandImpl();
        return mkDirCommand.execute(new MkDirCommandParamBuilder()
                .directory(getTargetDirectory(build))
                .build());
    }

    private File checkOutSource(String key, Project project, File targetDir,
        File elwoodLog) {

        final CloneCommand cloneCommand = new GitCloneCommandImpl(
                Collections.singletonList(new DefaultEventListener<>(
                        new GitCloneBuildMapWriter(
                                key, buildMapLog, elwoodLog))));

        final File checkedOutDir = new File(targetDir.getAbsoluteFile()
                + "/code");
        checkedOutDir.mkdirs();

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

    private Process createProcess(Project project, Build build, File checkedOutDir) {
        return new ProcessBuilderCommandImpl(new ShellCommandImpl(),
                new ShellCommandParamBuilder()).execute(
                toBuildModel(project, build, checkedOutDir));
    }

    private Integer buildProject(String key, Process process, File elwoodLog) {
        return new ProjectBuilderCommandImpl(new ShellCommandExecutorImpl(
                Collections.singletonList(new DefaultEventListener<>(
                        new ShellCommandBuildMapWriter(
                                key, buildMapLog, elwoodLog)))))
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

    private String getTargetDirectory(Build build) {
        return WORKSPACE_DIR + "/" + build.getWorkingDirectory();
    }

    private static class GitCloneBuildMapWriter
            implements Consumer<Event<GitCloneEventData>> {

        private final String key;

        private final BuildMapLog buildMapLog;

        private final File file;

        public GitCloneBuildMapWriter(String key, BuildMapLog buildMapLog,
            File file) {

            this.key = key;
            this.buildMapLog = buildMapLog;
            this.file = file;
        }

        @Override
        public void accept(Event<GitCloneEventData> event) {
            final byte[] data = event.getEventData().getData();
            try {
                final String content = new String(data, "UTF-8");
                write(content, file);
                buildMapLog.append(key, content);
            } catch (UnsupportedEncodingException e) {
                throw new BuildJobException(
                        "unable to clone source", e, key);
            }
        }

        private void write(String content, File file) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(content);
            } catch (IOException e) {
                throw new BuildJobException(
                        "unable to write to file", e, key);
            }
        }
    }

    private static class ShellCommandBuildMapWriter
            implements Consumer<Event<ShellCommandExecutorEventData>> {

        private final String key;

        private final BuildMapLog buildMapLog;

        private final File file;

        public ShellCommandBuildMapWriter(String key, BuildMapLog buildMapLog,
            File file) {

            this.key = key;
            this.buildMapLog = buildMapLog;
            this.file = file;
        }

        @Override
        public void accept(Event<ShellCommandExecutorEventData> event) {
            final byte[] data = event.getEventData().getData();
            try {
                final String content = new String(data, "UTF-8");
                write(content, file);
                buildMapLog.append(key, content);
            } catch (UnsupportedEncodingException e) {
                throw new BuildJobException(
                        "unsupported encoding", e, key);
            }
        }

        private void write(String content, File file) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(content);
            } catch (IOException e) {
                throw new BuildJobException(
                        "unable to write to file", e, key);
            }
        }
    }
}
