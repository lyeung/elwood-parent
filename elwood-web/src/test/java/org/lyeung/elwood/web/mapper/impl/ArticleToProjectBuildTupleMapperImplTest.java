package org.lyeung.elwood.web.mapper.impl;

import org.junit.Test;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 9/08/2015.
 */
public class ArticleToProjectBuildTupleMapperImplTest {

    private final ArticleToProjectBuildTupleMapperImpl impl
            = new ArticleToProjectBuildTupleMapperImpl();

    @Test
    public void testMap() {
        final ProjectBuildTuple tuple = impl.map(createArticle());
        final Project project = tuple.getProject();
        assertEquals("KEY", project.getKey());
        assertEquals("name", project.getName());
        assertEquals("description", project.getDescription());
        assertEquals("buildFile", project.getBuildFile());
        assertEquals(CloneCommandParam.AuthenticationType
                        .PUBLIC_KEY_PASSPHRASE.name(),
                project.getAuthenticationType());
        assertEquals("identityKey", project.getIdentityKey());
        assertEquals("passphrase", project.getPassphrase());
        assertEquals("sourceUrl", project.getSourceUrl());

        final Build build = tuple.getBuild();
        assertEquals("buildCommand", build.getBuildCommand());
        assertEquals("envVars", build.getEnvironmentVars());
        assertEquals("KEY", build.getWorkingDirectory());
//        assertNull(build.getWorkingDirectory());
        assertEquals("KEY", build.getKey());
    }

    private Article createArticle() {
        final Article article = new Article();
        article.setKey("key");
        article.setName("name");
        article.setDescription("description");
        article.setBuildCommand("buildCommand");
        article.setBuildFile("buildFile");
        article.setEnvironmentVars("envVars");
        article.setAuthenticationType(CloneCommandParam
                .AuthenticationType.PUBLIC_KEY_PASSPHRASE);
        article.setIdentityKey("identityKey");
        article.setPassphrase("passphrase");
        article.setSourceUrl("sourceUrl");

        return article;
    }
}