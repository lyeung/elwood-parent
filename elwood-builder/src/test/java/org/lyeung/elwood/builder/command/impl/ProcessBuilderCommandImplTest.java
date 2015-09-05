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

package org.lyeung.elwood.builder.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.builder.command.ProcessBuilderCommand;
import org.lyeung.elwood.builder.command.ProcessBuilderCommandException;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.builder.model.ModelStereotypeUtil;
import org.lyeung.elwood.common.command.ShellCommand;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.test.QuickTest;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 10/07/2015.
 */
@Category(value = QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class ProcessBuilderCommandImplTest {

    private ProcessBuilderCommand command;

    @Mock
    private ShellCommand shellCommand;

    @Mock
    private Process process;

    private BuildModel buildModel;

    @Before
    public void setUp() {
        ShellCommandParamBuilder paramBuilder = new ShellCommandParamBuilder();
        command = new ProcessBuilderCommandImpl(shellCommand, paramBuilder);
        buildModel = ModelStereotypeUtil.createBuildModel("echo hello", ModelStereotypeUtil.createProjectModel());
    }

    @Test
    public void testExecute() {
        when(shellCommand.execute(any(ShellCommandParam.class))).thenReturn(process);

        assertEquals(process, command.execute(buildModel));

        verify(shellCommand).execute(argThat(new ArgumentMatcher<ShellCommandParam>() {
            @Override
            public boolean matches(Object obj) {
                ShellCommandParam param = (ShellCommandParam) obj;
                return param.isRedirectErrorStream()
                        && param.getCommand().equals(buildModel.getBuildCommand())
                        && param.getEnvironmentVars().equals(buildModel.getEnvironmentVars())
                        && param.getDirectory().equals(buildModel.getWorkingDirectory());
            }
        }));

        verifyNoMoreInteractions(shellCommand);
    }

    @Test(expected = ProcessBuilderCommandException.class)
    public void testInvalidWorkingDirectory() {
        when(shellCommand.execute(any(ShellCommandParam.class)))
                .thenThrow(new ShellCommandException("mocked exception"));

        command.execute(buildModel);
    }
}