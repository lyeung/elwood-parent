package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;

import java.util.Optional;

/**
 * Created by lyeung on 25/12/2015.
 */
public interface GetBuildResultCommand extends Command<BuildResultKey, Optional<BuildResult>> {
    // do-nothing
}
