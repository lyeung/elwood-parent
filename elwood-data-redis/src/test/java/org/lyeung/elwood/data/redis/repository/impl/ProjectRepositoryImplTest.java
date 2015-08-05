package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Project;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 29/07/2015.
 */
@Category(SlowTest.class)
public class ProjectRepositoryImplTest extends AbstractRepositoryTest {

    private ProjectRepositoryImpl impl;

    @Before
    public void setUp() {
        impl = new ProjectRepositoryImpl("test:project", redisTemplate(Project.class));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll().stream().map(p -> p.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testGetOneAndSave() {
        Project project = ModelStereotypeUtil.createProject("PRJ-100");
        impl.save(project);

        final Project loadedProject = impl.getOne("PRJ-100");
        assertNotNull(loadedProject);
        assertEquals("PRJ-100", loadedProject.getKey());
        assertEquals("Project PRJ-100", loadedProject.getName());
        assertEquals("Project PRJ-100 description", loadedProject.getDescription());
        assertEquals("pom.xml", loadedProject.getBuildFile());
    }

    @Test
    public void testSave() {
        Project project1 = ModelStereotypeUtil.createProject("PRJ-100");
        impl.save(project1);

        Project project2 = ModelStereotypeUtil.createProject("PRJ-200");
        impl.save(project2);

        assertNotNull(impl.getOne("PRJ-100"));
        assertNotNull(impl.getOne("PRJ-200"));
        assertNull(impl.getOne("PRJ-300"));
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createProject("PRJ-100"));
        impl.save(ModelStereotypeUtil.createProject("PRJ-200"));

        final List<Project> result = impl.findAll();
        assertEquals(2, result.size());
        assertTrue(isContains("PRJ-100", result));
        assertTrue(isContains("PRJ-200", result));
        assertFalse(isContains("PRJ-300", result));
    }

    @Test
    public void testDelete() {
        impl.save(ModelStereotypeUtil.createProject("PRJ-100"));
        impl.save(ModelStereotypeUtil.createProject("PRJ-200"));

        deleteAll();
        assertEquals(0, impl.findAll().size());
    }

    private boolean isContains(String key, List<Project> projects) {
        return projects.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}
