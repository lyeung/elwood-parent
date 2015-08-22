package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.command.CommonConstants;
import org.lyeung.elwood.common.command.ShellCommand;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;

import java.io.File;
import java.io.IOException;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandImpl implements ShellCommand {

    @Override
    public Process execute(ShellCommandParam param) {
        try {
            final ProcessBuilder builder = new ProcessBuilder(
                    buildCommand(param.getCommand()))
                    .redirectErrorStream(param.isRedirectErrorStream())
                    .directory(new File(param.getDirectory()));

            builder.environment().putAll(new BuildEnvironmentHelper()
                    .buildEnvironment(param.getEnvironmentVars()));
            return builder.start();
        } catch (IOException e) {
            throw new ShellCommandException(
                    "unable to start shell command", e, param);
        }
    }


    private String[] buildCommand(String command) {
        return command.split(CommonConstants.SPACE_DELIMETER);
    }
}
