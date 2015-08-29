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
public class ShellCommandParam {

    private String command;

    private String directory;

    private boolean redirectErrorStream;

    private String environmentVars;

    ShellCommandParam() {
        // do-nothing
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isRedirectErrorStream() {
        return redirectErrorStream;
    }

    public void setRedirectErrorStream(boolean redirectErrorStream) {
        this.redirectErrorStream = redirectErrorStream;
    }

    public String getEnvironmentVars() {
        return environmentVars;
    }

    public void setEnvironmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
    }
}
