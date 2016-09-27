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

import com.github.lyeung.common.test.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by lyeung on 25/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SaveBuildResultMavenStatsCommandImplTest {

    @Mock
    private BuildResultMavenStatsRepository repository;

    private SaveBuildResultMavenStatsCommandImpl impl;


    @Before
    public void setUp() {
        impl = new SaveBuildResultMavenStatsCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        final BuildResultMavenStats value = new BuildResultMavenStats();
        final BuildResultKey key = new BuildResultKey(new BuildKey("PRJ"), 1L);
        value.setKey(key);

        final BuildResultMavenStats result = impl.execute(value);
        assertEquals(value, result);

        verify(repository).save(eq(value));
        verifyNoMoreInteractions(repository);
    }
}