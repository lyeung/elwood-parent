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

package org.lyeung.elwood.executor.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.KeyCountTuple;
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
@Ignore
public class BuildJobCommandImplTest {

    private static final String KEY = "KEY" + System.currentTimeMillis();

    private BuildJobCommandImpl impl;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BuildRepository buildRepository;

    @Mock
    private BuildMapLog buildMapLog;

    @Before
    public void setUp() throws IOException {
        assumeCiEnvironment();
        final File dir = new File("/tmp/workspace");
        if (dir.exists() && dir.isDirectory()) {
            FileUtils.forceDelete(dir);
        }

        MockitoAnnotations.initMocks(this);
        impl = new BuildJobCommandImpl(projectRepository, buildRepository, buildMapLog);
    }

    private void assumeCiEnvironment() {
        final Boolean skipRecursiveBuild = Boolean.valueOf(
                System.getenv("elwood.skipRecursiveBuild"));

        Assume.assumeFalse(skipRecursiveBuild);
        final String userHome = System.getProperty("user.home", "");
        Assume.assumeTrue(!userHome.equals(""));
        final File privateKey = new File(userHome + "/.ssh/id_rsa-elwood-project");
        Assume.assumeTrue(privateKey.exists() && privateKey.isFile());
    }

    @Test
    public void testExecute() {
        when(projectRepository.getOne(KEY)).thenReturn(createProject());
        when(buildRepository.getOne(KEY)).thenReturn(createBuild());

        final Integer resultStatus = impl.execute(new KeyCountTuple(KEY, 10L));
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
        project.setIdentityKey("src/test/resources/ssh-keys/id_rsa-elwood-project");
        project.setAuthenticationType(CloneCommandParam.AuthenticationType
                .PUBLIC_KEY_PASSPHRASE.name());

        return project;
    }
}