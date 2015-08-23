package org.lyeung.elwood.web.controller.build.command;

import org.lyeung.elwood.common.command.Command;
import org.lyeung.elwood.web.model.BuildJob;

/**
 * Created by lyeung on 3/08/2015.
 */
public interface GetBuildJobCommand extends Command<String, BuildJob> {
    // do-nothing
}