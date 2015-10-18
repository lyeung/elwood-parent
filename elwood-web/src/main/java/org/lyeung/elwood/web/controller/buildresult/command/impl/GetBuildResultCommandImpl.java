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

package org.lyeung.elwood.web.controller.buildresult.command.impl;

import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultResponse;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultsResponse;
import org.lyeung.elwood.web.controller.buildresult.command.GetBuildResultCommand;
import org.lyeung.elwood.web.controller.runbuild.KeyTuple;

import java.util.stream.Collectors;

/**
 * Created by lyeung on 6/10/2015.
 */
public class GetBuildResultCommandImpl implements GetBuildResultCommand {

    private final BuildResultRepository buildResultRepository;

    public GetBuildResultCommandImpl(BuildResultRepository buildResultRepository) {
        this.buildResultRepository = buildResultRepository;
    }

    @Override
    public GetBuildResultsResponse execute(KeyTuple keyTuple) {
        return new GetBuildResultsResponse(keyTuple, buildResultRepository.findAll(
                new BuildKey(keyTuple.getKey()), 0, 5).stream()
                .map(r -> createResponse(r))
                .collect(Collectors.toList()));
    }

    private GetBuildResultResponse createResponse(BuildResult buildResult) {
        return new GetBuildResultResponse(
                new KeyCountTuple(buildResult.getKey().getKey(), buildResult.getKey().getCount()),
                buildResult.getBuildStatus(), buildResult.getStartRunDate(),
                buildResult.getFinishRunDate());
    }
}
