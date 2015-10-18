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

package org.lyeung.elwood.web.controller.buildresult;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.lyeung.elwood.web.controller.runbuild.KeyTuple;

import java.util.List;

/**
 * Created by lyeung on 6/10/2015.
 */
public class GetBuildResultsResponse {

    private final KeyTuple keyTuple;

    private final List<GetBuildResultResponse> buildResultResponses;

    @JsonCreator
    public GetBuildResultsResponse(KeyTuple keyTuple,
        List<GetBuildResultResponse> buildResultResponses) {

        this.keyTuple = keyTuple;
        this.buildResultResponses = buildResultResponses;
    }

    public KeyTuple getKeyTuple() {
        return keyTuple;
    }

    public List<GetBuildResultResponse> getBuildResultResponses() {
        return buildResultResponses;
    }
}
