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

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandException extends SystemException {

    private ShellCommandParam param;

    public ShellCommandException(String message) {
        super(message);
    }

    public ShellCommandException(String message, Throwable cause, ShellCommandParam param) {
        super(message, cause);
        this.param = param;
    }

    public ShellCommandParam getParam() {
        return param;
    }
}
