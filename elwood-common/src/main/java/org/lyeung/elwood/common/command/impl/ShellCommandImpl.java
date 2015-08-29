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
