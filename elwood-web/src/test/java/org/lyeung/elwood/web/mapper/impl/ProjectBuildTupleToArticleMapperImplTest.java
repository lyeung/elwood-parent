package org.lyeung.elwood.web.mapper.impl;

import org.junit.Test;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 10/08/2015.
 */
public class ProjectBuildTupleToArticleMapperImplTest {

    private final ProjectBuildTupleToArticleMapperImpl impl =
            new ProjectBuildTupleToArticleMapperImpl();

    @Test
    public void testMap() {
        final Article article = impl.map(
                new ProjectBuildTuple(createProject(), createBuild()));

        assertEquals("KEY", article.getKey());
        assertEquals("name", article.getName());
        assertEquals("description", article.getDescription());
        assertEquals("buildFile", article.getBuildFile());
        assertEquals(CloneCommandParam.AuthenticationType.USERNAME_PASSWORD,
                article.getAuthenticationType());
        assertEquals("sourceUrl", article.getSourceUrl());
        assertEquals("identityKey", article.getIdentityKey());
        assertEquals("passphrase", article.getPassphrase());
        assertEquals("envVars", article.getEnvironmentVars());
        assertEquals("buildCommand", article.getBuildCommand());
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