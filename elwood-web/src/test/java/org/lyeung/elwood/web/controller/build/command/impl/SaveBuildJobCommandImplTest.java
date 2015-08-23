package org.lyeung.elwood.web.controller.build.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.controller.article.command.impl.SaveBuildJobCommandImpl;
import org.lyeung.elwood.web.model.BuildJob;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 22/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SaveBuildJobCommandImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BuildRepository buildRepository;

    private SaveBuildJobCommandImpl impl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        impl = new SaveBuildJobCommandImpl(projectRepository, buildRepository);
        final BuildJob buildJob = impl.execute(createArticle());
        assertEquals("KEY", buildJob.getKey());
        assertEquals("name", buildJob.getName());
        assertEquals("description", buildJob.getDescription());
        assertEquals("pom.xml", buildJob.getBuildFile());
        assertEquals("sourceUrl", buildJob.getSourceUrl());
        assertEquals("envVars", buildJob.getEnvironmentVars());
        assertEquals(CloneCommandParam.AuthenticationType.PUBLIC_KEY_PASSPHRASE,
                buildJob.getAuthenticationType());
        assertEquals("identityKey", buildJob.getIdentityKey());
        assertEquals("buildCommand", buildJob.getBuildCommand());
        assertEquals("passphrase", buildJob.getPassphrase());
    }

    private BuildJob createArticle() {
        final BuildJob buildJob = new BuildJob();
        buildJob.setKey("key");
        buildJob.setName("name");
        buildJob.setDescription("description");
        buildJob.setBuildFile("pom.xml");
        buildJob.setSourceUrl("sourceUrl");
        buildJob.setEnvironmentVars("envVars");
        buildJob.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE);
        buildJob.setIdentityKey("identityKey");
        buildJob.setBuildCommand("buildCommand");
        buildJob.setPassphrase("passphrase");

        return buildJob;
    }
}