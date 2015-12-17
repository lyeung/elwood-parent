package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.executor.command.AttachRunListenerCommand;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParam;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParam;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParamBuilder;

import java.io.File;

/**
 * Created by lyeung on 12/12/2015.
 */
public class AttachRunListenerCommandImpl implements AttachRunListenerCommand {

    private final AddSurefirePluginRunListenerCommand addSurefirePluginRunListenerCommand;

    public AttachRunListenerCommandImpl(
            AddSurefirePluginRunListenerCommand addSurefirePluginRunListenerCommand) {
        this.addSurefirePluginRunListenerCommand = addSurefirePluginRunListenerCommand;
    }

    @Override
    public String execute(AttachRunListenerCommandParam param) {
        final String command = param.getBuild().getBuildCommand();
        final String pomFile = findPomFile(command.split("\\s+"));

        final AddSurefirePluginRunListenerCommandParam commandParam =
                new AddSurefirePluginRunListenerCommandParamBuilder()
                        .pomFile(new File(param.getOutputDir(), pomFile).getAbsolutePath())
                        .build();
        return addSurefirePluginRunListenerCommand.execute(commandParam);
    }

    private String findPomFile(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (isPomFile(tokens[i])) {
                if (i + 1 < tokens.length) {
                    return tokens[i + 1];
                }
            }
        }

        return "pom.xml";
    }

    private boolean isPomFile(String token) {
        return token.equals("-f") || token.equals("--file");
    }
}
