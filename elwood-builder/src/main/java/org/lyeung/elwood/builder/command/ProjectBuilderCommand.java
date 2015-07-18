package org.lyeung.elwood.builder.command;

import org.lyeung.elwood.common.command.Command;

/**
 * Created by lyeung on 10/07/2015.
 */
public interface ProjectBuilderCommand<IN extends Process, OUT extends Integer> extends Command<IN, OUT> {
    // do-nothing
}
