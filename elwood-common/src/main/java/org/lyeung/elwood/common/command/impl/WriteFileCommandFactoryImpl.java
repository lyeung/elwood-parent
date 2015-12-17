package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.command.WriteFileCommand;
import org.lyeung.elwood.common.command.WriteFileCommandFactory;

/**
 * Created by lyeung on 16/12/2015.
 */
public class WriteFileCommandFactoryImpl implements WriteFileCommandFactory {

    @Override
    public WriteFileCommand makeCommand() {
        return new WriteFileCommandImpl();
    }
}
