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

package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandParamBuilder {

    private String command;

    private String directory;

    private boolean redirectErrorStream;

    private String environmentVars;

    public String getCommand() {
        return command;
    }

    public ShellCommandParamBuilder command(String command) {
        this.command = command;
        return this;
    }

    public ShellCommandParamBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }

    public ShellCommandParamBuilder redirectErrorStream(boolean redirectErrorStream) {
        this.redirectErrorStream = redirectErrorStream;
        return this;
    }

    public ShellCommandParamBuilder environmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
        return this;
    }

    public ShellCommandParam build() {
        ShellCommandParam param = new ShellCommandParam();
        param.setCommand(command);
        param.setDirectory(directory);
        param.setEnvironmentVars(environmentVars);
        param.setRedirectErrorStream(redirectErrorStream);

        return param;
    }
}
