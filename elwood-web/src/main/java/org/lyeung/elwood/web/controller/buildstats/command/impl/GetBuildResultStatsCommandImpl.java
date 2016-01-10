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
