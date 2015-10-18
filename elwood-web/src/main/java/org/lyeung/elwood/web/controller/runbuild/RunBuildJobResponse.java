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

package org.lyeung.elwood.web.controller.runbuild;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultResponse;

/**
 * Created by lyeung on 2/09/2015.
 */
public class RunBuildJobResponse {

    private final KeyCountTuple keyCountTuple;

    private final GetBuildResultResponse buildResultResponse;

    @JsonCreator
    public RunBuildJobResponse(KeyCountTuple keyCountTuple,
        GetBuildResultResponse buildResultResponse) {

        this.keyCountTuple = keyCountTuple;
        this.buildResultResponse = buildResultResponse;
    }

    public KeyCountTuple getKeyCountTuple() {
        return keyCountTuple;
    }

    public GetBuildResultResponse getBuildResultResponse() {
        return buildResultResponse;
    }
}
