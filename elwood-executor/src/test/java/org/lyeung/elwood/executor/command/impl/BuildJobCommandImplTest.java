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
import org.lyeung.elwood.common.command.WriteFileCommand;
import org.lyeung.elwood.common.command.WriteFileCommandFactory;
import org.lyeung.elwood.common.command.WriteFileCommandParam;
import org.lyeung.elwood.common.test.QuickTest;
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
import org.lyeung.elwood.executor.command.AttachRunListenerCommand;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandFactory;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParam;
import org.lyeung.elwood.executor.command.CheckoutDirCreatorCommandFactory;
import org.lyeung.elwood.executor.command.ElwoodLogFileCreatorCommandFactory;
import org.lyeung.elwood.executor.command.FileCreatorCommand;
import org.lyeung.elwood.executor.command.GetMavenStatusCommand;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandFactory;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandParam;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommand;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommandFactory;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandFactory;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    private AttachRunListenerCommandFactory attachRunListenerCommandFactory;

    @Mock
    private WriteFileCommandFactory writeFileCommandFactory;

    @Mock
    private ProcessBuilderCommandFactory processBuilderCommandFactory;

    @Mock
    private ProjectBuilderCommandFactory projectBuilderCommandFactory;

    @Mock
    private GetMavenStatusCommandFactory getMavenStatusCommandFactory;

    @Mock
    private SaveBuildResultMavenStatsCommandFactory saveBuildResultMavenStatsCommandFactory;

    @Mock
    private MkDirCommand mkDirCommand;

    @Mock
    private FileCreatorCommand checkoutDirCreatorCommand;

    @Mock
    private FileCreatorCommand elwoodLogFileCreatorCommand;

    @Mock
    private CloneCommand cloneCommand;

    @Mock
    private AttachRunListenerCommand attachRunListenerCommand;

    @Mock
    private WriteFileCommand writeFileCommand;

    @Mock
    private ProcessBuilderCommand processBuilderCommand;

    @Mock
    private Process process;

    @Mock
    private ProjectBuilderCommand projectBuilderCommand;

    @Mock
    private BuildResultRepository buildResultRepository;

    @Mock
    private GetMavenStatusCommand getMavenStatusCommand;

    @Mock
    private SaveBuildResultMavenStatsCommand saveBuildResultMavenStatsCommand;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        impl = new BuildJobCommandImpl(new BuildJobCommandImpl.Param()
                .buildMapLog(buildMapLog)
                .buildRepository(buildRepository)
                .projectRepository(projectRepository)
                .mkDirCommandFactory(mkDirCommandFactory)
                .checkOutDirCreatorCommandFactory(checkoutDirCreatorCommandFactory)
                .elwoodLogFileCreatorCommandFactory(elwoodLogFileCreatorCommandFactory)
                .cloneCommandFactory(cloneCommandFactory)
                .attachRunListenerCommandFactory(attachRunListenerCommandFactory)
                .writeFileCommandFactory(writeFileCommandFactory)
                .processBuilderCommandFactory(processBuilderCommandFactory)
                .projectBuilderCommandFactory(projectBuilderCommandFactory)
                .buildResultRepository(buildResultRepository)
                .getMavenStatusCommandFactory(getMavenStatusCommandFactory)
                .saveBuildResultMavenStatsCommandFactory(saveBuildResultMavenStatsCommandFactory));
    }

    @Test
    public void testExecute() {
        final ProjectKey projectKey = new ProjectKey(KEY);
        when(projectRepository.getOne(projectKey)).thenReturn(of(createProject()));
        final BuildKey buildKey = new BuildKey(KEY);
        when(buildRepository.getOne(buildKey)).thenReturn(of(createBuild()));

        // mkdir command
        when(mkDirCommandFactory.makeCommand()).thenReturn(mkDirCommand);
        final File targetDir = mock(File.class);
        when(mkDirCommand.execute(any(MkDirCommandParam.class))).thenReturn(targetDir);

        // checkout dir command
        when(checkoutDirCreatorCommandFactory.makeCommand()).thenReturn(checkoutDirCreatorCommand);
        final File checkoutDir = new File("checkOutDir");
        when(checkoutDirCreatorCommand.execute(targetDir)).thenReturn(checkoutDir);

        // elwood log command
        when(elwoodLogFileCreatorCommandFactory.makeCommand())
                .thenReturn(elwoodLogFileCreatorCommand);
        final File elwoodLog = mock(File.class);
        when(elwoodLogFileCreatorCommand.execute(targetDir)).thenReturn(elwoodLog);

        // clone command
        when(cloneCommandFactory.makeCommand(anyList())).thenReturn(cloneCommand);
        when(cloneCommand.execute(any(CloneCommandParam.class))).thenReturn(checkoutDir);

        when(attachRunListenerCommandFactory.makeCommand()).thenReturn(attachRunListenerCommand);
        when(attachRunListenerCommand.execute(any(AttachRunListenerCommandParam.class)))
                .thenReturn("content");

        when(writeFileCommandFactory.makeCommand()).thenReturn(writeFileCommand);
        when(writeFileCommand.execute(any(WriteFileCommandParam.class)))
                .thenReturn(new File("updated-pom.xml"));

        // process command
        when(processBuilderCommandFactory.makeCommand(any(ShellCommand.class)))
                .thenReturn(processBuilderCommand);
        when(processBuilderCommand.execute(any(BuildModel.class))).thenReturn(process);

        // project command
        when(projectBuilderCommandFactory.makeCommand(any(ShellCommandExecutor.class)))
                .thenReturn(projectBuilderCommand);
        when(projectBuilderCommand.execute(process)).thenReturn(0);


        // build result repository
        final BuildResult buildResult = new BuildResult();
        final BuildResultKey buildResultKey = new BuildResultKey(buildKey, 10L);
        buildResult.setKey(buildResultKey);
        when(buildResultRepository.getOne(buildResultKey))
                .thenReturn(of(buildResult));

        when(getMavenStatusCommandFactory.makeCommand()).thenReturn(getMavenStatusCommand);
        when(saveBuildResultMavenStatsCommandFactory.makeCommand()).thenReturn(saveBuildResultMavenStatsCommand);

        when(buildMapLog.removeFuture(new KeyCountTuple(KEY, 10L))).thenReturn(true);
        when(buildMapLog.removeContent(new KeyCountTuple(KEY, 10L))).thenReturn(true);

        final Integer resultStatus = impl.execute(new KeyCountTuple(KEY, 10L));
        assertEquals(0, resultStatus.intValue());
        assertEquals(BuildStatus.SUCCEEDED, buildResult.getBuildStatus());
        assertNotNull(buildResult.getFinishRunDate());

        verify(buildMapLog).removeFuture(eq(new KeyCountTuple(KEY, 10L)));
        verify(buildMapLog).removeContent(eq(new KeyCountTuple(KEY, 10L)));

        verify(projectRepository).getOne(projectKey);
        verify(buildRepository).getOne(buildKey);
        verify(mkDirCommandFactory).makeCommand();
        verify(mkDirCommand).execute(any(MkDirCommandParam.class));
        verify(checkoutDirCreatorCommandFactory).makeCommand();
        verify(checkoutDirCreatorCommand).execute(targetDir);
        verify(elwoodLogFileCreatorCommandFactory).makeCommand();
        verify(elwoodLogFileCreatorCommand).execute(eq(targetDir));
        verify(cloneCommandFactory).makeCommand(anyList());
        verify(cloneCommand).execute(any(CloneCommandParam.class));
        verify(attachRunListenerCommandFactory).makeCommand();
        verify(attachRunListenerCommand).execute(any(AttachRunListenerCommandParam.class));
        verify(writeFileCommandFactory).makeCommand();
        verify(writeFileCommand).execute(any(WriteFileCommandParam.class));
        verify(processBuilderCommandFactory).makeCommand(any(ShellCommand.class));
        verify(processBuilderCommand).execute(any(BuildModel.class));
        verify(projectBuilderCommandFactory).makeCommand(any(ShellCommandExecutor.class));
        verify(projectBuilderCommand).execute(eq(process));
        verify(buildResultRepository).getOne(eq(buildResultKey));
        verify(buildResultRepository).save(eq(buildResult));
        verify(getMavenStatusCommandFactory).makeCommand();
        verify(getMavenStatusCommand).execute(argThat(new ArgumentMatcher<GetMavenStatusCommandParam>() {
            @Override
            public boolean matches(Object argument) {
                final GetMavenStatusCommandParam param = (GetMavenStatusCommandParam) argument;
                return param.getCheckedOutDir() == checkoutDir
                        && param.getBuildResultKey().equals(buildResultKey);
            }
        }));
        verify(saveBuildResultMavenStatsCommandFactory).makeCommand();
        verify(saveBuildResultMavenStatsCommand).execute(any(BuildResultMavenStats.class));

        verifyNoMoreInteractions(projectRepository);
        verifyNoMoreInteractions(buildRepository);
        verifyNoMoreInteractions(mkDirCommandFactory);
        verifyNoMoreInteractions(mkDirCommand);
        verifyNoMoreInteractions(checkoutDirCreatorCommandFactory);
        verifyNoMoreInteractions(checkoutDirCreatorCommand);
        verifyNoMoreInteractions(elwoodLogFileCreatorCommandFactory);
        verifyNoMoreInteractions(elwoodLogFileCreatorCommand);
        verifyNoMoreInteractions(cloneCommandFactory);
        verifyNoMoreInteractions(cloneCommand);
        verifyNoMoreInteractions(attachRunListenerCommandFactory);
        verifyNoMoreInteractions(attachRunListenerCommand);
        verifyNoMoreInteractions(writeFileCommandFactory);
        verifyNoMoreInteractions(writeFileCommand);
        verifyNoMoreInteractions(processBuilderCommandFactory);
        verifyNoMoreInteractions(processBuilderCommand);
        verifyNoMoreInteractions(projectBuilderCommandFactory);
        verifyNoMoreInteractions(projectBuilderCommand);
        verifyNoMoreInteractions(getMavenStatusCommandFactory);
        verifyNoMoreInteractions(getMavenStatusCommand);
        verifyNoMoreInteractions(saveBuildResultMavenStatsCommandFactory);
        verifyNoMoreInteractions(saveBuildResultMavenStatsCommand);
        verifyNoMoreInteractions(buildResultRepository);
        verifyNoMoreInteractions(buildMapLog);
    }

    private Build createBuild() {
        final Build build = new Build();
        build.setBuildCommand("mvn clean install");
        build.setWorkingDirectory(KEY);

        return build;
    }

    private Project createProject() {
        final Project project = new Project();
        project.setKey(new ProjectKey(KEY));
        project.setPassphrase(null);
        project.setSourceUrl("git@bitbucket.org:lyeung/elwood-parent.git");
        project.setIdentityKey("src/test/resources/ssh-keys/id_rsa-elwood-project");
        project.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE.name());

        return project;
    }
}