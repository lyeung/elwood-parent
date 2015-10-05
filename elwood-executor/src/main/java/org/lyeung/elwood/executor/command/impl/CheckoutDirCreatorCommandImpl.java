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

package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.executor.command.CheckoutDirCreatorCommand;

import java.io.File;

/**
 * Created by lyeung on 1/09/2015.
 */
public class CheckoutDirCreatorCommandImpl implements CheckoutDirCreatorCommand {

    private static final String CODE_PATH = "/code";

    @Override
    public File execute(File file) {
        final File checkedOutDir = new File(file.getAbsoluteFile() + CODE_PATH);
        // TODO: add check to return value
        checkedOutDir.mkdirs();

        return checkedOutDir;
    }
}