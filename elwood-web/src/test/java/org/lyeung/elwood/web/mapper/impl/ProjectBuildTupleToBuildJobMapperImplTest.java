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