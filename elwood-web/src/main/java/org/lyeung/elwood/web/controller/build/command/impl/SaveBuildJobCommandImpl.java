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

package org.lyeung.elwood.web.controller.build.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.mapper.impl.BuildJobToProjectBuildTupleMapperImpl;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToBuildJobMapperImpl;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class SaveBuildJobCommandImpl implements SaveBuildJobCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public SaveBuildJobCommandImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public BuildJob execute(BuildJob buildJob) {
        final ProjectBuildTuple tuple =
                new BuildJobToProjectBuildTupleMapperImpl().map(buildJob);
        projectRepository.save(tuple.getProject());
        buildRepository.save(tuple.getBuild());

        return new ProjectBuildTupleToBuildJobMapperImpl().map(tuple);
    }

}
