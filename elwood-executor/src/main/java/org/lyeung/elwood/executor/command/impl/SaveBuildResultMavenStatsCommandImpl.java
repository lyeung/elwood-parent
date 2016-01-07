package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommand;

/**
 * Created by lyeung on 25/12/2015.
 */
public class SaveBuildResultMavenStatsCommandImpl implements SaveBuildResultMavenStatsCommand {

    private final BuildResultMavenStatsRepository repository;

    public SaveBuildResultMavenStatsCommandImpl(BuildResultMavenStatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public BuildResultMavenStats execute(BuildResultMavenStats value) {
        repository.save(value);

        return value;
    }
}
