package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Project;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lyeung on 29/07/2015.
 */
@Category(SlowTest.class)
public class ProjectRepositoryImplTest extends AbstractRepositoryTest {

    private ProjectRepositoryImpl impl;

    @Before
    public void setUp() throws Exception {
        impl = new ProjectRepositoryImpl("test:project", redisTemplate(Project.class));
    }

    @Test
    public void testGetOneAndSave() throws Exception {
        Project project = ModelStereotypeUtil.createProject("100");
        impl.save(project);

        final Project loadedProject = impl.getOne("PRJ-100");
        assertNotNull(loadedProject);
        assertEquals("PRJ-100", loadedProject.getKey());
        assertEquals("Project 100", loadedProject.getName());
        assertEquals("Project 100 description", loadedProject.getDescription());
        assertEquals("pom.xml", loadedProject.getBuildFile());
    }

    @Test
    public void testSave() throws Exception {
        Project project1 = ModelStereotypeUtil.createProject("100");
        impl.save(project1);

        Project project2 = ModelStereotypeUtil.createProject("200");
        impl.save(project2);

        assertNotNull(impl.getOne(project1.getKey()));
        assertNotNull(impl.getOne(project2.getKey()));

        impl.delete(Arrays.asList(project1.getKey(), project2.getKey()));
        assertNull(impl.getOne(project1.getKey()));
        assertNull(impl.getOne(project2.getKey()));
    }
}
