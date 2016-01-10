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