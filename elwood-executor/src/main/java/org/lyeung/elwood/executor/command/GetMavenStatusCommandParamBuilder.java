/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;

import java.io.File;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandParamBuilder {

    private File checkedOutDir;

    private BuildResultKey buildResultKey;

    public GetMavenStatusCommandParamBuilder checkedOutDir(File checkedOutDir) {
        this.checkedOutDir = checkedOutDir;
        return this;
    }

    public GetMavenStatusCommandParamBuilder buildResultKey(BuildResultKey buildResultKey) {
        this.buildResultKey = buildResultKey;
        return this;
    }

    public GetMavenStatusCommandParam build() {
        final GetMavenStatusCommandParam param = new GetMavenStatusCommandParam();
        param.setBuildResultKey(buildResultKey);
        param.setCheckedOutDir(checkedOutDir);

        return param;
    }
}
