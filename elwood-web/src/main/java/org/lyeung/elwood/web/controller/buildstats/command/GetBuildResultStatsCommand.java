package org.lyeung.elwood.web.controller.buildstats.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.web.controller.buildstats.BuildResultStats;

/**
 * Created by lyeung on 10/01/2016.
 */
public interface GetBuildResultStatsCommand extends Command<BuildResultKey, BuildResultStats> {
    // do-nothing
}
