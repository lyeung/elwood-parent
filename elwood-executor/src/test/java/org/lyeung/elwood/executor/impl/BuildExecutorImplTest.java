package org.lyeung.elwood.executor.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 21/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildExecutorImplTest {

    @Mock
    private BuildJobCommandFactory factory;

    @Mock
    private ExecutorService executorService;

    @Mock
    private Future<Integer> future;

    private BuildExecutor buildExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() {
        when(executorService.submit(any(BuildTask.class))).thenReturn(future);
        buildExecutor = new BuildExecutorImpl(factory, executorService);
        assertEquals(future, buildExecutor.add("key"));

        verify(executorService).submit(any(BuildTask.class));

        verifyZeroInteractions(factory);
        verifyNoMoreInteractions(executorService);
    }
}