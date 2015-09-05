/*
 *
 *  Copyright (C) 2015 the original author or authors.
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

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.builder.command.ProcessBuilderCommand;
import org.lyeung.elwood.builder.command.ProcessBuilderCommandFactory;
import org.lyeung.elwood.builder.command.ProjectBuilderCommand;
import org.lyeung.elwood.builder.command.ProjectBuilderCommandFactory;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.common.command.MkDirCommand;
import org.lyeung.elwood.common.command.MkDirCommandFactory;
import org.lyeung.elwood.common.command.MkDirCommandParam;
import org.lyeung.elwood.common.command.ShellCommand;
import org.lyeung.elwood.common.command.ShellCommandExecutor;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.CheckoutDirCreatorCommandFactory;
import org.lyeung.elwood.executor.command.ElwoodLogFileCreatorCommandFactory;
import org.lyeung.elwood.executor.command.FileCreatorCommand;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandFactory;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 18/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildJobCommandImplTest {

    private static final String KEY = "KEY" + System.currentTimeMillis();

    private BuildJobCommandImpl impl;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BuildRepository buildRepository;

    @Mock
    private BuildMapLog buildMapLog;

    @Mock
    private MkDirCommandFactory mkDirCommandFactory;

    @Mock
    private CheckoutDirCreatorCommandFactory checkoutDirCreatorCommandFactory;

    @Mock
    private ElwoodLogFileCreatorCommandFactory elwoodLogFileCreatorCommandFactory;

    @Mock
    private CloneCommandFactory cloneCommandFactory;

    @Mock
    private ProcessBuilderCommandFactory processBuilderCommandFactory;

    @Mock
    private ProjectBuilderCommandFactory projectBuilderCommandFactory;

    @Mock
    private MkDirCommand mkDirCommand;

    @Mock
    private FileCreatorCommand checkoutDirCreatorCommand;

    @Mock
    private FileCreatorCommand elwoodLogFileCreatorCommand;

    @Mock
    private CloneCommand cloneCommand;

    @Mock
    private ProcessBuilderCommand processBuilderCommand;

    @Mock
    private Process process;

    @Mock
    private ProjectBuilderCommand projectBuilderCommand;

    @Mock
    private BuildResultRepository buildResultRepository;

    @Before
    public void setUp() throws IOException {
//        final File dir = new File("/tmp/workspace");
//        if (dir.exists() && dir.isDirectory()) {
//            FileUtils.forceDelete(dir);
//        }

        MockitoAnnotations.initMocks(this);
        impl = new BuildJobCommandImpl(new BuildJobCommandImpl.Param()
                .buildMapLog(buildMapLog)
                .buildRepository(buildRepository)
                .projectRepository(projectRepository)
                .mkDirCommandFactory(mkDirCommandFactory)
                .checkOutDirCreatorCommandFactory(checkoutDirCreatorCommandFactory)
                .elwoodLogFileCreatorCommandFactory(elwoodLogFileCreatorCommandFactory)
                .cloneCommandFactory(cloneCommandFactory)
                .processBuilderCommandFactory(processBuilderCommandFactory)
                .projectBuilderCommandFactory(projectBuilderCommandFactory)
                .buildResultRepository(buildResultRepository));
    }

    @Test
    public void testExecute() {
        when(projectRepository.getOne(KEY)).thenReturn(createProject());
        when(buildRepository.getOne(KEY)).thenReturn(createBuild());

        // mkdir command
        when(mkDirCommandFactory.createMkDirCommand()).thenReturn(mkDirCommand);
        final File targetDir = mock(File.class);
        when(mkDirCommand.execute(any(MkDirCommandParam.class))).thenReturn(targetDir);

        // checkout dir command
        when(checkoutDirCreatorCommandFactory.makeCommand()).thenReturn(checkoutDirCreatorCommand);
        final File checkoutDir = mock(File.class);
        when(checkoutDirCreatorCommand.execute(targetDir)).thenReturn(checkoutDir);

        // elwood log command
        when(elwoodLogFileCreatorCommandFactory.makeCommand())
                .thenReturn(elwoodLogFileCreatorCommand);
        final File elwoodLog = mock(File.class);
        when(elwoodLogFileCreatorCommand.execute(targetDir)).thenReturn(elwoodLog);

        // clone command
        when(cloneCommandFactory.makeCommand(any(List.class))).thenReturn(cloneCommand);
        when(cloneCommand.execute(any(CloneCommandParam.class))).thenReturn(checkoutDir);

        // process command
        when(processBuilderCommandFactory.makeCommand(any(ShellCommand.class),
                any(ShellCommandParamBuilder.class))).thenReturn(processBuilderCommand);
        when(processBuilderCommand.execute(any(BuildModel.class))).thenReturn(process);

        // project command
        when(projectBuilderCommandFactory.makeCommand(any(ShellCommandExecutor.class)))
                .thenReturn(projectBuilderCommand);
        when(projectBuilderCommand.execute(process)).thenReturn(0);

        // build result repository
        final BuildResult buildResult = new BuildResult();
        when(buildResultRepository.getOne(new KeyCountTuple(KEY, 10L).toString()))
                .thenReturn(buildResult);

        when(buildMapLog.removeFuture(new KeyCountTuple(KEY, 10L))).thenReturn(true);
        when(buildMapLog.removeContent(new KeyCountTuple(KEY, 10L))).thenReturn(true);

        final Integer resultStatus = impl.execute(new KeyCountTuple(KEY, 10L));
        assertEquals(0, resultStatus.intValue());

    }

    private Build createBuild() {
        final Build build = new Build();
        build.setBuildCommand("mvn clean install");
        build.setWorkingDirectory(KEY);

        return build;
    }

    private Project createProject() {
        final Project project = new Project();
        project.setKey(KEY);
        project.setPassphrase(null);
        project.setSourceUrl("git@bitbucket.org:lyeung/elwood-parent.git");
        project.setIdentityKey("src/test/resources/ssh-keys/id_rsa-elwood-project");
        project.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE.name());

        return project;
    }
}