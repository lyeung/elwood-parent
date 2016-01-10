package org.lyeung.elwood.web.controller.buildstats;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.web.controller.buildstats.command.GetBuildResultStatsCommand;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 10/01/2016.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildResultStatsControllerTest {

    @InjectMocks
    private BuildResultStatsController controller;

    @Mock
    private GetBuildResultStatsCommand command;

    @Test
    public void testGetBuildResultStats() throws Exception {
        final BuildResultStats stats = new BuildResultStats(3, 2, 1);
        when(command.execute(new BuildResultKey(new BuildKey("PRJ"), 2)))
            .thenReturn(stats);
        assertEquals(stats, controller.getBuildResultStats("PRJ", 2));
    }
}