package org.lyeung.elwood.builder.command.impl;

import org.lyeung.elwood.builder.command.ProjectBuilderCommand;
import org.lyeung.elwood.builder.command.ProjectBuilderCommandException;
import org.lyeung.elwood.common.SystemException;
import org.lyeung.elwood.common.command.ShellCommandExecutor;

/**
 * Created by lyeung on 9/07/2015.
 */
public class ProjectBuilderCommandImpl implements ProjectBuilderCommand<Process, Integer> {

    private ShellCommandExecutor command;

    public ProjectBuilderCommandImpl(ShellCommandExecutor command) {
        this.command = command;
    }

    @Override
    public Integer execute(Process process) {
        try {
            return command.execute(process);
        } catch (SystemException e) {
            throw new ProjectBuilderCommandException("unable to execute project builder command", e);
        }
    }
}
