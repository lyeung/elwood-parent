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

package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.domain.ProjectKey;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 29/07/2015.
 */
@Category(SlowTest.class)
public class ProjectRepositoryImplTest extends AbstractRepositoryTest {

    private ProjectRepositoryImpl impl;

    @Before
    public void setUp() {
        impl = new ProjectRepositoryImpl("test/project",
                new RedisHashRepositoryImpl<>(redisTemplate(Project.class)));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll(0, -1).stream().map(p -> p.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testGetOneAndSave() {
        Project project = ModelStereotypeUtil.createProject(new ProjectKey("PRJ"));
        impl.save(project);

        final Optional<Project> loadedProject = impl.getOne(new ProjectKey("PRJ"));
        assertTrue(loadedProject.isPresent());
        assertEquals("PRJ", loadedProject.get().getKey().toStringValue());
        assertEquals("Project PRJ", loadedProject.get().getName());
        assertEquals("Project PRJ description", loadedProject.get().getDescription());
        assertEquals("pom.xml", loadedProject.get().getBuildFile());
    }

    @Test
    public void testSave() {
        Project project1 = ModelStereotypeUtil.createProject(new ProjectKey("PRJ"));
        impl.save(project1);

        Project project2 = ModelStereotypeUtil.createProject(new ProjectKey("PRJ2"));
        impl.save(project2);

        assertTrue(impl.getOne(new ProjectKey("PRJ")).isPresent());
        assertTrue(impl.getOne(new ProjectKey("PRJ2")).isPresent());
        assertFalse(impl.getOne(new ProjectKey("PRJ3")).isPresent());
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createProject(new ProjectKey("PRJ")));
        impl.save(ModelStereotypeUtil.createProject(new ProjectKey("PRJ2")));

        final List<Project> result = impl.findAll(0, -1);
        assertEquals(2, result.size());
        assertTrue(isContains(new ProjectKey("PRJ"), result));
        assertTrue(isContains(new ProjectKey("PRJ2"), result));
        assertFalse(isContains(new ProjectKey("PRJ3"), result));
    }

    @Test
    public void testDelete() {
        impl.save(ModelStereotypeUtil.createProject(new ProjectKey("PRJ")));
        impl.save(ModelStereotypeUtil.createProject(new ProjectKey("PRJ2")));

        deleteAll();
        assertEquals(0, impl.findAll(0, -1).size());
    }

    private boolean isContains(ProjectKey key, List<Project> projects) {
        return projects.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}
