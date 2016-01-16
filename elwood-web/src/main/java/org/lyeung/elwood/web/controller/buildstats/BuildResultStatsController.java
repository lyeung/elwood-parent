/*
 *
 *  Copyright (C) 2015-2016 the original author or authors.
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

package org.lyeung.elwood.web.controller.buildstats;

import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.buildstats.command.GetBuildResultStatsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 10/01/2016.
 */
@RequestMapping(value = NavigationConstants.BUILD_RESULT_STATS)
@RestController
public class BuildResultStatsController {

    @Autowired
    private GetBuildResultStatsCommand buildResultStatsCommand;

    @RequestMapping(value = "/{key}/{count}", method = RequestMethod.GET)
    public BuildResultStats getBuildResultStats(
            @PathVariable("key") String key, @PathVariable("count") int count) {
        return buildResultStatsCommand.execute(new BuildResultKey(new BuildKey(key), count));
    }
}
