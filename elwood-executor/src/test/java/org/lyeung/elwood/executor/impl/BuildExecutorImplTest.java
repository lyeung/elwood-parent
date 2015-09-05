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

package org.lyeung.elwood.executor.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
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

    private static final String KEY = "key";

    private static final long COUNT = 10L;

    private static final KeyCountTuple KEY_COUNT_TUPLE = new KeyCountTuple(KEY, COUNT);

    @Mock
    private BuildJobCommandFactory factory;

    @Mock
    private ExecutorService executorService;

    @Mock
    private Future<Integer> future;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() {
        when(executorService.submit(any(BuildTask.class))).thenReturn(future);
        final BuildExecutor buildExecutor = new BuildExecutorImpl(factory, executorService);
        assertEquals(future, buildExecutor.add(KEY_COUNT_TUPLE));

        verify(executorService).submit(argThat(new ArgumentMatcher<BuildTask>() {
            @Override
            public boolean matches(Object argument) {
                final BuildTask buildTask = (BuildTask) argument;
                return buildTask.getKeyCountTuple().getKey().equals(KEY.toUpperCase())
                        && buildTask.getKeyCountTuple().getCount() == 10L;
            }
        }));

        verifyZeroInteractions(factory);
        verifyNoMoreInteractions(executorService);
    }
}