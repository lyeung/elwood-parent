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

package org.lyeung.elwood.vcs.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 16/07/2015.
 */
public class CloneCommandException extends SystemException {

    private final CloneCommandParam cloneCommandParam;

    public CloneCommandException(String message,
        CloneCommandParam cloneCommandParam) {

        super(message);
        this.cloneCommandParam = cloneCommandParam;
    }

    public CloneCommandException(String message, Throwable cause,
        CloneCommandParam cloneCommandParam) {

        super(message, cause);
        this.cloneCommandParam = cloneCommandParam;
    }

    public CloneCommandParam getCloneCommandParam() {
        return cloneCommandParam;
    }
}
