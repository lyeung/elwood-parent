package org.lyeung.elwood.builder.command.impl;

import org.lyeung.elwood.builder.command.ProcessBuilderCommand;
import org.lyeung.elwood.builder.command.ProcessBuilderCommandException;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.common.command.ShellCommand;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;

/**
 * Created by lyeung on 9/07/15.
 */
public class ProcessBuilderCommandImpl implements ProcessBuilderCommand<BuildModel, Process> {

    private final ShellCommand shellCommand;

    private final ShellCommandParamBuilder paramBuilder;

    public ProcessBuilderCommandImpl(ShellCommand shellCommand, ShellCommandParamBuilder paramBuilder) {
        this.shellCommand = shellCommand;
        this.paramBuilder = paramBuilder;
    }

    @Override
    public Process execute(BuildModel buildModel) {
        try {
            final ShellCommandParam param = paramBuilder.command(buildModel.getBuildCommand())
                    .directory(buildModel.getWorkingDirectory())
                    .environmentVars(buildModel.getEnvironmentVars())
                    .redirectErrorStream(true)
                    .build();
            return shellCommand.execute(param);
        } catch (ShellCommandException e) {
            throw new ProcessBuilderCommandException("unable to execute process", e, buildModel);
        }
    }
}
