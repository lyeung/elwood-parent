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

    private ProjectBuilderCommand<Process, Integer> command;

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
