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
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildResultRepositoryImplTest extends AbstractRepositoryTest {

    private BuildResultRepositoryImpl impl;

    @Before
    public void setUp() {
        impl = new BuildResultRepositoryImpl("test:buildResult", redisTemplate(BuildResult.class));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll().stream().map(b -> b.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testGetOne() {
        final BuildResult buildResult = ModelStereotypeUtil.createBuildResult("PRJ-100");
        impl.save(buildResult);

        final BuildResult result = impl.getOne("PRJ-100");
        assertNotNull(result);
        assertEquals("PRJ-100", result.getKey());
        assertEquals(BuildStatus.SUCCEEDED, result.getBuildStatus());
        assertEquals(buildResult.getStartRunDate(), result.getStartRunDate());
        assertEquals(buildResult.getFinishRunDate(), result.getFinishRunDate());
    }

    @Test
    public void testSave() {
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-101"));

        assertNotNull(impl.getOne("PRJ-100"));
        assertNotNull(impl.getOne("PRJ-101"));
        assertNull(impl.getOne("PRJ-000"));
    }

    @Test
    public void testDelete() {
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-101"));

        assertFalse(impl.findAll().isEmpty());
        impl.delete(Arrays.asList("PRJ-100", "PRJ-101"));
        assertTrue(impl.findAll().isEmpty());
    }

    @Test
    public void testFindAll() {
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-100"));
        impl.save(ModelStereotypeUtil.createBuildResult("PRJ-101"));

        final List<BuildResult> result = impl.findAll();
        assertEquals(2, result.size());
        assertTrue(isContains("PRJ-100", result));
        assertTrue(isContains("PRJ-101", result));
        assertFalse(isContains("PRJ-000", result));
    }

    private boolean isContains(String key, List<BuildResult> buildResults) {
        return buildResults.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}