package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 9/07/15.
 */
public interface Command<IN, OUT> {

    OUT execute(IN in);

}
