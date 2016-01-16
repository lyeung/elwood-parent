/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 25/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetBuildResultCommandImplTest {

    @Mock
    private BuildResultRepository repository;

    private GetBuildResultCommandImpl impl;

    @Before
    public void setUp() {
        impl = new GetBuildResultCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        final BuildResultKey buildResultKey = new BuildResultKey(new BuildKey("PRJ"), 1L);
        final BuildResult buildResult = new BuildResult();
        buildResult.setKey(buildResultKey);

        final Optional<BuildResult> optionalResult = Optional.of(buildResult);
        when(repository.getOne(buildResultKey)).thenReturn(optionalResult);

        final Optional<BuildResult> result = impl.execute(buildResultKey);
        assertTrue(result.isPresent());
        assertEquals(result.get(), optionalResult.get());

        verify(repository).getOne(buildResultKey);
        verifyNoMoreInteractions(repository);
    }
}