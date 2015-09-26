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

package org.lyeung.elwood.web.mapper.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class ProjectBuildTupleToBuildJobMapperImpl
        implements Mapper<ProjectBuildTuple, BuildJob> {

    @Override
    public BuildJob map(ProjectBuildTuple tuple) {
        return toBuildJob(tuple.getProject(), tuple.getBuild());
    }

    private BuildJob toBuildJob(Project project, Build build) {
        final BuildJob buildJob = new BuildJob();
        buildJob.setKey(project.getKey().toStringValue());
        buildJob.setName(project.getName());
        buildJob.setDescription(project.getDescription());
        buildJob.setBuildFile(project.getBuildFile());
        buildJob.setSourceUrl(project.getSourceUrl());
        buildJob.setAuthenticationType(CloneCommandParam.AuthenticationType
                .valueOf(project.getAuthenticationType()));
        buildJob.setIdentityKey(project.getIdentityKey());
        buildJob.setPassphrase(project.getPassphrase());

        buildJob.setBuildCommand(build.getBuildCommand());
        buildJob.setEnvironmentVars(build.getEnvironmentVars());

        return buildJob;
    }

}
