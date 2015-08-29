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

package org.lyeung.elwood.web.controller.runbuild;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.IncrementBuildCountCommand;
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

    private static final String KEY = "KEY";

    @InjectMocks
    private RunBuildJobController controller;

    @Mock
    private BuildExecutor buildExecutor;

    @Mock
    private BuildMapLog buildMapLog;

    @Mock
    private IncrementBuildCountCommand incrementBuildCountCommand;

    @Mock
    private Future<Integer> future;

    @Test
    public void testRunBuildJob() {
        when(incrementBuildCountCommand.execute(KEY)).thenReturn(2L);
        when(buildExecutor.add(KEY, 2L)).thenReturn(future);

        final KeyTuple keyTuple = new KeyTuple();
        keyTuple.setKey("KEY");
        controller.runBuildJob(keyTuple);

        verify(incrementBuildCountCommand).execute(eq(KEY));
        verify(buildExecutor).add(eq(KEY), eq(2L));
        verifyNoMoreInteractions(buildExecutor);
        verifyZeroInteractions(buildMapLog);
    }

    @Test
    public void testGetContentByKey() {
        List<String> content = Arrays.asList("hello", "world");
        when(buildMapLog.get(KEY)).thenReturn(Optional.of(content));

        final ContentResponse response = controller.getContentByKey(KEY);
        assertEquals("helloworld", response.getContent());
    }

    @Test
    public void testEmptyGetContentByKey() {
        when(buildMapLog.get(KEY)).thenReturn(Optional.<List<String>>empty());

        final ContentResponse response = controller.getContentByKey(KEY);
        assertNull(response.getContent());
    }
}