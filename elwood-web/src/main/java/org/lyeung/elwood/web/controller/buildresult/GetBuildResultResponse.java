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
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;
import org.lyeung.elwood.executor.command.KeyCountTuple;

import java.util.Date;

/**
 * Created by lyeung on 6/10/2015.
 */
public class GetBuildResultResponse {

    private final KeyCountTuple keyCountTuple;

    private final BuildStatus buildStatus;

    private final Date startRunDate;

    private final Date finishRunDate;

    @JsonCreator
    public GetBuildResultResponse(KeyCountTuple keyCountTuple, BuildStatus buildStatus,
        Date startRunDate, Date finishRunDate) {
        this.keyCountTuple = keyCountTuple;
        this.buildStatus = buildStatus;
        this.startRunDate = startRunDate;
        this.finishRunDate = finishRunDate;
    }

    public KeyCountTuple getKeyCountTuple() {
        return keyCountTuple;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public Date getStartRunDate() {
        return startRunDate;
    }

    public Date getFinishRunDate() {
        return finishRunDate;
    }
}
