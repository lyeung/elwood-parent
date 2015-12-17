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

package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildTask implements Callable<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildTask.class);

    private final BuildJobCommandFactory buildJobCommandFactory;

    private final KeyCountTuple keyCountTuple;

    public BuildTask(BuildJobCommandFactory buildJobCommandFactory, KeyCountTuple keyCountTuple) {
        this.buildJobCommandFactory = buildJobCommandFactory;
        this.keyCountTuple = keyCountTuple;
    }

    public KeyCountTuple getKeyCountTuple() {
        return keyCountTuple;
    }

    @Override
    public Integer call() throws Exception {
        try {
            return buildJobCommandFactory
                    .makeCommand()
                    .execute(keyCountTuple);
        } catch (Throwable e) {
            LOGGER.error("unable to execute build key-count=[" + keyCountTuple + "]", e);
            return -100;
        }
    }
}
