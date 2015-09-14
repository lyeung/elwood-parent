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
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildResultRepositoryImplTest extends AbstractRepositoryTest {

    private static final String HASH_KEY = "test/buildResult";

    private BuildResultRepositoryImpl impl;

    private HashRepository<BuildResult, String, String> repository;

    @Before
    public void setUp() {
        repository = new RedisHashRepositoryImpl<>(redisTemplate(BuildResult.class));
        impl = new BuildResultRepositoryImpl(repository);
        deleteAll();
    }

    private void deleteAll() {
//        impl.findAll(HASH_KEY, 0, -1).stream().forEach(i -> impl.delete(HASH_KEY, i));
        impl.delete(HASH_KEY, impl.findAll(HASH_KEY, 0, -1).stream()
                .map(i -> i.getKey()).collect(Collectors.toList()));
    }

    @Test
    public void testFindAll() {
        final String key = "PRJ-100";
        final BuildResult buildResult = ModelStereotypeUtil.createBuildResult(key);

        impl.save(HASH_KEY, buildResult);

        final List<BuildResult> result = impl.findAll(HASH_KEY, 0, -1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(key, buildResult.getKey());
        assertEquals(BuildStatus.SUCCEEDED, result.iterator().next().getBuildStatus());
        assertEquals(buildResult.getStartRunDate(), result.iterator().next().getStartRunDate());
        assertEquals(buildResult.getFinishRunDate(), result.iterator().next().getFinishRunDate());

        assertTrue(impl.findAll("none", 0, -1).isEmpty());
    }

    @Test
    public void testSave() {
        final String key1 = "PRJ-100";
        final BuildResult buildResult1 = ModelStereotypeUtil.createBuildResult(key1);
        impl.save(HASH_KEY, buildResult1);

        final String key2 = "PRJ-101";
        final BuildResult buildResult2 = ModelStereotypeUtil.createBuildResult(key2);
        impl.save(HASH_KEY, buildResult2);

        final List<BuildResult> result = impl.findAll(HASH_KEY, 0, -1);
        assertEquals(2, result.size());
        assertTrue(isContains(key1, result));
        assertTrue(isContains(key1, result));
    }

    @Test
    public void testDelete() {
        final BuildResult buildResult1 = ModelStereotypeUtil.createBuildResult("PRJ-100");
        impl.save(HASH_KEY, buildResult1);

        final BuildResult buildResult2 = ModelStereotypeUtil.createBuildResult("PRJ-101");
        impl.save(HASH_KEY, buildResult2);

        assertFalse(impl.findAll(HASH_KEY, 0, -1).isEmpty());
        impl.delete(HASH_KEY, Arrays.asList(buildResult1.getKey(), buildResult2.getKey()));
        assertTrue(impl.findAll(HASH_KEY, 0, -1).isEmpty());
    }

    private boolean isContains(String key, List<BuildResult> buildResults) {
        return buildResults.stream().map(p -> p.getKey()).filter(k -> k.equals(key)).count() > 0;
    }
}