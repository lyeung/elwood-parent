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
