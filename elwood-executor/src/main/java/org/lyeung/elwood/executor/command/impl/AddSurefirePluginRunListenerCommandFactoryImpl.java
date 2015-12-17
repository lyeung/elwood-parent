package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.executor.command.AddSurefirePluginRunListenerCommandFactory;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.lyeung.elwood.maven.command.impl.AddSurefirePluginRunListenerCommandImpl;

/**
 * Created by lyeung on 17/12/2015.
 */
public class AddSurefirePluginRunListenerCommandFactoryImpl
        implements AddSurefirePluginRunListenerCommandFactory {

    private final PomModelManager pomModelManager;

    public AddSurefirePluginRunListenerCommandFactoryImpl(PomModelManager pomModelManager) {
        this.pomModelManager = pomModelManager;
    }

    @Override
    public AddSurefirePluginRunListenerCommand makeCommand() {
        return new AddSurefirePluginRunListenerCommandImpl(pomModelManager);
    }
}
