package org.lyeung.elwood.executor.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.command.BuildJobCommand;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 20/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildTaskTest {

    @Mock
    private BuildJobCommandFactory factory;

    @Mock
    private BuildJobCommand command;

    private BuildTask buildTask;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCall() throws Exception {
        when(factory.makeCommand()).thenReturn(command);
        when(command.execute("key")).thenReturn(1);

        final BuildTask task = new BuildTask(factory, "key");
        assertEquals(1, task.call().intValue());

        verify(factory).makeCommand();
        verify(command).execute(eq("key"));
        verifyNoMoreInteractions(factory);
        verifyNoMoreInteractions(command);
    }
}
