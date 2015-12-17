package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;

/**
 * Created by lyeung on 17/12/2015.
 */
public interface AddSurefirePluginRunListenerCommandFactory {

    AddSurefirePluginRunListenerCommand makeCommand();
}
