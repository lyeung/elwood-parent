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

import org.junit.Test;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 10/08/2015.
 */
public class ProjectBuildTupleToBuildJobMapperImplTest {

    private final ProjectBuildTupleToBuildJobMapperImpl impl =
            new ProjectBuildTupleToBuildJobMapperImpl();

    @Test
    public void testMap() {
        final BuildJob buildJob = impl.map(
                new ProjectBuildTuple(createProject(), createBuild()));

        assertEquals("KEY", buildJob.getKey());
        assertEquals("name", buildJob.getName());
        assertEquals("description", buildJob.getDescription());
        assertEquals("buildFile", buildJob.getBuildFile());
        assertEquals(CloneCommandParam.AuthenticationType.USERNAME_PASSWORD,
                buildJob.getAuthenticationType());
        assertEquals("sourceUrl", buildJob.getSourceUrl());
        assertEquals("identityKey", buildJob.getIdentityKey());
        assertEquals("passphrase", buildJob.getPassphrase());
        assertEquals("envVars", buildJob.getEnvironmentVars());
        assertEquals("buildCommand", buildJob.getBuildCommand());
    }

    private Project createProject() {
        final Project project = new Project();
        project.setKey("KEY");
        project.setName("name");
        project.setDescription("description");
        project.setBuildFile("buildFile");
        project.setSourceUrl("sourceUrl");
        project.setAuthenticationType(CloneCommandParam.AuthenticationType
                .USERNAME_PASSWORD.name());
        project.setIdentityKey("identityKey");
        project.setPassphrase("passphrase");

        return project;
    }

    private Build createBuild() {
        final Build build = new Build();
        build.setEnvironmentVars("envVars");
        build.setWorkingDirectory("/tmp/workingDir");
        build.setBuildCommand("buildCommand");
        build.setKey("KEY");

        return build;
    }
}