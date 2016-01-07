package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommand;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommandFactory;

/**
 * Created by lyeung on 26/12/2015.
 */
public class SaveBuildResultMavenStatsCommandFactoryImpl
        implements SaveBuildResultMavenStatsCommandFactory {

    private final BuildResultMavenStatsRepository repository;

    public SaveBuildResultMavenStatsCommandFactoryImpl(BuildResultMavenStatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public SaveBuildResultMavenStatsCommand makeCommand() {
        return new SaveBuildResultMavenStatsCommandImpl(repository);
    }
}
