/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
public class ProcessBuilderCommandImpl implements ProcessBuilderCommand {

    private final ShellCommand shellCommand;

    private final ShellCommandParamBuilder paramBuilder;

    public ProcessBuilderCommandImpl(
            ShellCommand shellCommand, ShellCommandParamBuilder paramBuilder) {

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
