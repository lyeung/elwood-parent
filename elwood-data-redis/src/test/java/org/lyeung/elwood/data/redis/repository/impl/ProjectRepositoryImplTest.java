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
        Project project = ModelStereotypeUtil.createProject("PRJ-100");
        impl.save(project);

        final Optional<Project> loadedProject = impl.getOne("PRJ-100");
        assertTrue(loadedProject.isPresent());
        assertEquals("PRJ-100", loadedProject.get().getKey());
        assertEquals("Project PRJ-100", loadedProject.get().getName());
        assertEquals("Project PRJ-100 description", loadedProject.get().getDescription());
        assertEquals("pom.xml", loadedProject.get().getBuildFile());
    }

    @Test
    public void testSave() {
        Project project1 = ModelStereotypeUtil.createProject("PRJ-100");
        impl.save(project1);

        Project project2 = ModelStereotypeUtil.createProject("PRJ-200");
        impl.save(project2);

        assertTrue(impl.getOne("PRJ-100").isPresent());
        assertTrue(impl.getOne("PRJ-200").isPresent());
        assertFalse(impl.getOne("PRJ-300").isPresent());
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createProject("PRJ-100"));
        impl.save(ModelStereotypeUtil.createProject("PRJ-200"));

        final List<Project> result = impl.findAll(0, -1);
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
        assertEquals(0, impl.findAll(0, -1).size());
    }

    private boolean isContains(String key, List<Project> projects) {
        return projects.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}
