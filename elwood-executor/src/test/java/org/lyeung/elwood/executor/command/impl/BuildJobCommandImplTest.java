package org.lyeung.elwood.executor.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 18/08/2015.
 */
@Category(SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class BuildJobCommandImplTest {

    private static final String KEY = "KEY123";

    private BuildJobCommandImpl impl;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BuildRepository buildRepository;

    @Mock
    private BuildMapLog buildMapLog;

    @Before
    public void setUp() throws IOException {
        FileUtils.forceDelete(new File("/tmp/workspace"));
        MockitoAnnotations.initMocks(this);
        impl = new BuildJobCommandImpl(projectRepository, buildRepository, buildMapLog);
    }

    @Test
    public void testExecute() {
        when(projectRepository.getOne(KEY)).thenReturn(createProject());
        when(buildRepository.getOne(KEY)).thenReturn(createBuild());

        final Integer resultStatus = impl.execute(KEY);
        assertEquals(0, resultStatus.intValue());
    }

    private Build createBuild() {
        final Build build = new Build();
        build.setBuildCommand("mvn clean install");
        build.setWorkingDirectory(KEY);

        return build;
    }

    private Project createProject() {
        final Project project = new Project();
        project.setKey(KEY);
        project.setPassphrase(null);
        project.setSourceUrl("git@bitbucket.org:lyeung/elwood-parent.git");
        project.setIdentityKey("/Users/lyeung/.ssh/id_rsa-elwood-project");
        project.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE.name());

        return project;
    }
}