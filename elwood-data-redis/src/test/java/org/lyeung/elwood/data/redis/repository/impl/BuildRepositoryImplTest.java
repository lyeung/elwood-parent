package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Build;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 1/08/2015.
 */
@Category(SlowTest.class)
public class BuildRepositoryImplTest extends AbstractRepositoryTest {

    private BuildRepositoryImpl impl;

    @Before
    public void setUp() {
        impl = new BuildRepositoryImpl("test:build", redisTemplate(Build.class));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll().stream().map(p -> p.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testGetOneAndSave() {
        final Build build = ModelStereotypeUtil.createBuild("PRJ-100");
        impl.save(build);

        final Build loadedBuild = impl.getOne("PRJ-100");
        assertNotNull(loadedBuild);
        assertEquals("PRJ-100", loadedBuild.getKey());
        assertEquals("workingDirectory", loadedBuild.getWorkingDirectory());
        assertEquals("buildDirectory", loadedBuild.getBuildCommand());
        assertEquals("environmentVars", loadedBuild.getEnvironmentVars());
    }

    @Test
    public void testSave() {
        impl.save(ModelStereotypeUtil.createBuild("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuild("PRJ-200"));

        assertNotNull(impl.getOne("PRJ-100"));
        assertNotNull(impl.getOne("PRJ-200"));
        assertNull(impl.getOne("PRJ-300"));
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createBuild("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuild("PRJ-200"));

        final List<Build> builds = impl.findAll();
        assertEquals(2, builds.size());
        assertTrue(isContains("PRJ-100", builds));
        assertTrue(isContains("PRJ-200", builds));
        assertFalse(isContains("PRJ-300", builds));
    }

    @Test
    public void testDelete() {
        impl.save(ModelStereotypeUtil.createBuild("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuild("PRJ-200"));

        deleteAll();
        assertEquals(0, impl.findAll().size());
    }

    private boolean isContains(String key, List<Build> builds) {
        return builds.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}
