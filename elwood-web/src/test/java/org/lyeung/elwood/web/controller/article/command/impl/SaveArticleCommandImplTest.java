package org.lyeung.elwood.web.controller.article.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.model.Article;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 22/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SaveArticleCommandImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BuildRepository buildRepository;

    private SaveArticleCommandImpl impl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        impl = new SaveArticleCommandImpl(projectRepository, buildRepository);
        final Article article = impl.execute(createArticle());
        assertEquals("KEY", article.getKey());
        assertEquals("name", article.getName());
        assertEquals("description", article.getDescription());
        assertEquals("pom.xml", article.getBuildFile());
        assertEquals("sourceUrl", article.getSourceUrl());
        assertEquals("envVars", article.getEnvironmentVars());
        assertEquals(CloneCommandParam.AuthenticationType.PUBLIC_KEY_PASSPHRASE,
                article.getAuthenticationType());
        assertEquals("identityKey", article.getIdentityKey());
        assertEquals("buildCommand", article.getBuildCommand());
        assertEquals("passphrase", article.getPassphrase());
    }

    private Article createArticle() {
        final Article article = new Article();
        article.setKey("key");
        article.setName("name");
        article.setDescription("description");
        article.setBuildFile("pom.xml");
        article.setSourceUrl("sourceUrl");
        article.setEnvironmentVars("envVars");
        article.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE);
        article.setIdentityKey("identityKey");
        article.setBuildCommand("buildCommand");
        article.setPassphrase("passphrase");

        return article;
    }
}