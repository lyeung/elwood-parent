package org.lyeung.elwood.web.controller.runbuild;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 22/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class RunBuildJobControllerTest {

    @InjectMocks
    private RunBuildJobController controller;

    @Mock
    private BuildExecutor buildExecutor;

    @Mock
    private BuildMapLog buildMapLog;

    @Mock
    private Future<Integer> future;

    @Test
    public void testRunBuildJob() {
        when(buildExecutor.add("KEY")).thenReturn(future);

        controller.runBuildJob(new KeyTuple("KEY"));

        verify(buildExecutor).add(eq("KEY"));
        verifyNoMoreInteractions(buildExecutor);
        verifyZeroInteractions(buildMapLog);
    }

    @Test
    public void testGetContentByKey() {
        List<String> content = Arrays.asList("hello", "world");
        when(buildMapLog.get("KEY")).thenReturn(Optional.of(content));

        final ContentResponse response = controller.getContentByKey("KEY");
        assertEquals("helloworld", response.getContent());
    }

    @Test
    public void testEmptyGetContentByKey() {
        when(buildMapLog.get("KEY")).thenReturn(Optional.<List<String>>empty());

        final ContentResponse response = controller.getContentByKey("KEY");
        assertNull(response.getContent());
    }
}