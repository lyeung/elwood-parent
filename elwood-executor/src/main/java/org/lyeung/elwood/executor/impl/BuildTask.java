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

import java.util.concurrent.Callable;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildTask implements Callable<Integer> {

    private final BuildJobCommandFactory buildJobCommandFactory;

    private final String key;

    private final long count;

    public BuildTask(BuildJobCommandFactory buildJobCommandFactory, String key, long count) {
        this.buildJobCommandFactory = buildJobCommandFactory;
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public long getCount() {
        return count;
    }

    @Override
    public Integer call() throws Exception {
        return buildJobCommandFactory
                .makeCommand()
                .execute(new KeyCountTuple(key, count));
    }
}
