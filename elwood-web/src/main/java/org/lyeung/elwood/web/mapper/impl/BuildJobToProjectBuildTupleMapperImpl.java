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
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.domain.ProjectKey;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class BuildJobToProjectBuildTupleMapperImpl
        implements Mapper<BuildJob, ProjectBuildTuple> {

    @Override
    public ProjectBuildTuple map(BuildJob buildJob) {
        return new ProjectBuildTuple(toProject(buildJob), toBuild(buildJob));
    }

    private Project toProject(BuildJob buildJob) {
        final Project project = new Project();
        project.setKey(new ProjectKey(buildJob.getKey().toUpperCase()));
        project.setName(buildJob.getName());
        project.setDescription(buildJob.getDescription());
        project.setBuildFile(buildJob.getBuildFile());
        project.setSourceUrl(buildJob.getSourceUrl());
        project.setAuthenticationType(buildJob.getAuthenticationType().name());
        project.setIdentityKey(buildJob.getIdentityKey());
        project.setPassphrase(buildJob.getPassphrase());

        return project;
    }

    private Build toBuild(BuildJob buildJob) {
        final Build build = new Build();
        build.setBuildCommand(buildJob.getBuildCommand());
        build.setKey(new BuildKey(buildJob.getKey().toUpperCase()));
        build.setEnvironmentVars(buildJob.getEnvironmentVars());
        build.setWorkingDirectory(buildJob.getKey().toUpperCase());

        return build;
    }
}
