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
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.BuildKey;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 1/08/2015.
 */
@Category(SlowTest.class)
public class BuildRepositoryImplTest extends AbstractRepositoryTest {

    private BuildRepositoryImpl impl;

    @Before
    public void setUp() {
        impl = new BuildRepositoryImpl("test/build",
                new RedisHashRepositoryImpl<>(redisTemplate()));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll(0, -1).stream().map(p -> p.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testGetOneAndSave() {
        final Build build = ModelStereotypeUtil.createBuild(new BuildKey("PRJ"));
        impl.save(build);

        final Optional<Build> loadedBuild = impl.getOne(new BuildKey("PRJ"));
        assertTrue(loadedBuild.isPresent());
        assertEquals("PRJ", loadedBuild.get().getKey().toStringValue());
        assertEquals("workingDirectory", loadedBuild.get().getWorkingDirectory());
        assertEquals("buildDirectory", loadedBuild.get().getBuildCommand());
        assertEquals("environmentVars", loadedBuild.get().getEnvironmentVars());
    }

    @Test
    public void testSave() {
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ")));
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ2")));

        assertTrue(impl.getOne(new BuildKey("PRJ")).isPresent());
        assertTrue(impl.getOne(new BuildKey("PRJ2")).isPresent());
        assertFalse(impl.getOne(new BuildKey("PRJ3")).isPresent());
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ")));
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ2")));

        final List<Build> builds = impl.findAll(0, -1);
        assertEquals(2, builds.size());
        assertTrue(isContains(new BuildKey("PRJ"), builds));
        assertTrue(isContains(new BuildKey("PRJ2"), builds));
        assertFalse(isContains(new BuildKey("PRJ3"), builds));
    }

    @Test
    public void testDelete() {
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ")));
        impl.save(ModelStereotypeUtil.createBuild(new BuildKey("PRJ200")));

        deleteAll();
        assertEquals(0, impl.findAll(0, -1).size());
    }

    private boolean isContains(BuildKey key, List<Build> builds) {
        return builds.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}
