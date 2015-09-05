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
import org.lyeung.elwood.builder.command.ProjectBuilderCommand;
import org.lyeung.elwood.builder.command.ProjectBuilderCommandException;
import org.lyeung.elwood.common.command.ShellCommandExecutor;
import org.lyeung.elwood.common.command.ShellCommandExecutorException;
import org.lyeung.elwood.common.test.QuickTest;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 11/07/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class ProjectBuilderCommandImplTest {

    private ProjectBuilderCommand command;

    @Mock
    private ShellCommandExecutor shellCommandExecutor;

    @Mock
    private Process process;

    @Before
    public void setUp() {
        command = new ProjectBuilderCommandImpl(shellCommandExecutor);
    }

    @Test
    public void testExecute() throws Exception {
        when(shellCommandExecutor.execute(process)).thenReturn(0);

        final Integer exitStatus = command.execute(process);

        assertEquals(0, exitStatus.intValue());
        verify(shellCommandExecutor).execute(eq(process));
    }


    @Test(expected = ProjectBuilderCommandException.class)
    public void testExecuteException() throws Exception {
        when(shellCommandExecutor.execute(process)).thenThrow(new ShellCommandExecutorException("mocked exception"));

        command.execute(process);

    }
}
