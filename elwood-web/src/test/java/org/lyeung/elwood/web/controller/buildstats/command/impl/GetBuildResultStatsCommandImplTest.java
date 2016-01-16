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

package org.lyeung.elwood.web.controller.buildstats.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.web.controller.buildstats.BuildResultStats;
import org.lyeung.elwood.web.controller.buildstats.command.GetBuildResultStatsCommand;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 10/01/2016.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetBuildResultStatsCommandImplTest {

    @Mock
    private BuildResultMavenStatsRepository repository;

    private GetBuildResultStatsCommand command;

    @Test
    public void testExecute() {
        final BuildResultMavenStats stats = createMavenStats();
        when(repository.getOne(new BuildResultKey(new BuildKey("PRJ"), 2)))
                .thenReturn(Optional.of(stats));

        final BuildResultStats result = new GetBuildResultStatsCommandImpl(repository)
                .execute(new BuildResultKey(new BuildKey("PRJ"), 2));
        assertNotNull(result);
        assertEquals(3, result.getSuccessCount());
        assertEquals(2, result.getFailedCount());
        assertEquals(1, result.getIgnoredCount());

        verify(repository).getOne(eq(new BuildResultKey(new BuildKey("PRJ"), 2)));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testExecuteNone() {
        when(repository.getOne(new BuildResultKey(new BuildKey("PRJ"), 2)))
                .thenReturn(Optional.empty());

        final BuildResultStats result = new GetBuildResultStatsCommandImpl(repository)
                .execute(new BuildResultKey(new BuildKey("PRJ"), 2));
        assertNotNull(result);
        assertEquals(BuildResultStats.NONE, result);

        verify(repository).getOne(eq(new BuildResultKey(new BuildKey("PRJ"), 2)));
        verifyNoMoreInteractions(repository);
    }

    private BuildResultMavenStats createMavenStats() {
        final BuildResultMavenStats stats = new BuildResultMavenStats();
        stats.setSuccessCount(3);
        stats.setFailedCount(2);
        stats.setIgnoredCount(1);

        return stats;
    }
}