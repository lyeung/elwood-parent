package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.data.redis.domain.BuildResult;

/**
 * Created by lyeung on 25/12/2015.
 */
public interface SaveBuildResultCommand extends Command<BuildResult, BuildResult> {
    // do-nothing
}
