package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.executor.command.AddSurefirePluginRunListenerCommandFactory;
import org.lyeung.elwood.executor.command.AttachRunListenerCommand;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandFactory;

/**
 * Created by lyeung on 12/12/2015.
 */
public class AttachRunListenerCommandFactoryImpl implements AttachRunListenerCommandFactory {

    private final AddSurefirePluginRunListenerCommandFactory addRunListenerCommandFactory;

    public AttachRunListenerCommandFactoryImpl(
            AddSurefirePluginRunListenerCommandFactory addRunListenerCommandFactory) {
        this.addRunListenerCommandFactory = addRunListenerCommandFactory;
    }

    @Override
    public AttachRunListenerCommand makeCommand() {
        return new AttachRunListenerCommandImpl(addRunListenerCommandFactory.makeCommand());
    }
}
