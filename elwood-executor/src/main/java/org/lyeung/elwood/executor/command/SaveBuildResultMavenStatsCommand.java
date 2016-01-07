package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;

/**
 * Created by lyeung on 25/12/2015.
 */
public interface SaveBuildResultMavenStatsCommand
        extends Command<BuildResultMavenStats, BuildResultMavenStats> {
    // do-nothing
}
