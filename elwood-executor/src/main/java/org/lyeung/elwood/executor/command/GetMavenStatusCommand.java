package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;

/**
 * Created by lyeung on 3/01/2016.
 */
public interface GetMavenStatusCommand
        extends Command<GetMavenStatusCommandParam, BuildResultMavenStats> {
    // do-nothing
}
