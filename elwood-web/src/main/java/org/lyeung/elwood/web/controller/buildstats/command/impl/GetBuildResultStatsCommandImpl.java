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

import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.web.controller.buildstats.BuildResultStats;
import org.lyeung.elwood.web.controller.buildstats.command.GetBuildResultStatsCommand;

import java.util.Optional;

/**
 * Created by lyeung on 10/01/2016.
 */
public class GetBuildResultStatsCommandImpl implements GetBuildResultStatsCommand {

    private final BuildResultMavenStatsRepository repository;

    public GetBuildResultStatsCommandImpl(BuildResultMavenStatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public BuildResultStats execute(BuildResultKey buildResultKey) {
        Optional<BuildResultMavenStats> optional = repository.getOne(buildResultKey);
        if (!optional.isPresent()) {
            return BuildResultStats.NONE;
        }

        BuildResultMavenStats stats = optional.get();
        return new BuildResultStats(stats.getSuccessCount(), stats.getFailedCount(),
                stats.getIgnoredCount());
    }
}
