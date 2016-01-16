/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.data.redis.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 25/12/2015.
 */
public class BuildResultMavenStatsRepositoryImplTest extends AbstractRepositoryTest {

    private static final BuildResultKey BUILD_RESULT_KEY = new BuildResultKey(
            new BuildKey("PRJ"), 2L);

    private BuildResultMavenStatsRepository impl;

    private HashRepository<BuildResultMavenStats, String, String> repository;

    @Before
    public void setUp() {
        repository = new RedisHashRepositoryImpl<>(redisTemplate());
        impl = new BuildResultMavenStatsRepositoryImpl("test/buildResultMavenStats", repository);
        deleteAll();
    }

    private void deleteAll() {
        final Optional<BuildResultMavenStats> result = impl.getOne(BUILD_RESULT_KEY);
        if (result.isPresent()) {
            impl.delete(Collections.singletonList(result.get().getKey()));
        }
    }

    @Test
    public void testSaveAndGetOne() {
        final BuildResultMavenStats stats = ModelStereotypeUtil.createBuildResultMavenStats(
                BUILD_RESULT_KEY);
        impl.save(stats);

        final Optional<BuildResultMavenStats> result = impl.getOne(BUILD_RESULT_KEY);
        assertTrue(result.isPresent());

        assertEquals(BUILD_RESULT_KEY, result.get().getKey());
        assertEquals(1, result.get().getSuccessCount());
        assertEquals(2, result.get().getIgnoredCount());
        assertEquals(3, result.get().getFailedCount());
    }

    @Test
    public void testDelete() throws Exception {
        final BuildResultMavenStats stats = ModelStereotypeUtil.createBuildResultMavenStats(
                BUILD_RESULT_KEY);
        impl.save(stats);

        Optional<BuildResultMavenStats> result = impl.getOne(BUILD_RESULT_KEY);
        assertTrue(result.isPresent());

        impl.delete(Collections.singletonList(result.get().getKey()));

        result = impl.getOne(BUILD_RESULT_KEY);
        assertFalse(result.isPresent());
    }

}