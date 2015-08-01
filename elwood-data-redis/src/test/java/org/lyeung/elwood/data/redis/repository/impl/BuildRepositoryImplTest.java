package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Build;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 1/08/2015.
 */
@Category(SlowTest.class)
public class BuildRepositoryImplTest extends AbstractRepositoryTest {

    private BuildRepositoryImpl impl;

    @Before
    public void setUp() throws Exception {
        impl = new BuildRepositoryImpl("test:build", redisTemplate(Build.class));
    }

    @Test
    public void testGetOneAndSave() throws Exception {
        final Build build = ModelStereotypeUtil.createBuild("100");
        impl.save(build);

        final Build loadedBuild = impl.getOne("PRJ-100");
        assertNotNull(loadedBuild);
        assertEquals("PRJ-100", loadedBuild.getProject().getKey());
        assertEquals("Project 100", loadedBuild.getProject().getName());
        assertEquals("Project 100 description", loadedBuild.getProject().getDescription());
        assertEquals("pom.xml", loadedBuild.getProject().getBuildFile());
        assertEquals("workingDirectory", loadedBuild.getWorkingDirectory());
        assertEquals("buildDirectory", loadedBuild.getBuildCommand());
        assertEquals("environmentVars", loadedBuild.getEnvironmentVars());
    }
}
