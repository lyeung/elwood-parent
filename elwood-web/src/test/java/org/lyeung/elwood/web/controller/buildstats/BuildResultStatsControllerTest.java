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