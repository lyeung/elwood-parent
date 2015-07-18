package org.lyeung.elwood.builder.command;

import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.common.command.Command;

/**
 * Created by lyeung on 10/07/2015.
 */
public interface ProcessBuilderCommand<IN extends BuildModel, OUT extends Process> extends Command<IN, OUT> {
   // do-nothing
}
