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
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 24/08/2015.
 */
@Category(SlowTest.class)
public class BuildCountRepositoryImplTest extends AbstractRepositoryTest {

    private static final String HASH_KEY = "test/buildCount";

    private BuildCountRepositoryImpl impl;

    @Before
    public void setUp() {
        final RedisTemplate<String, Long> template = redisCountTemplate();
        impl = new BuildCountRepositoryImpl(HASH_KEY, new CountRepositoryImpl<>(template));
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll().stream().collect(Collectors.toList()));
    }

    @Test
    public void testGetOne() {
        impl.incrementBy("PRJ-100", 1L);

        final Long count = impl.getCount("PRJ-100");
        assertNotNull(count);
        assertEquals(1L, count.longValue());

        assertNull(impl.getCount("PRJ-200"));
    }

    @Test
    public void testFindAll() {
        impl.incrementBy("PRJ-100", 1L);
        impl.incrementBy("PRJ-200", 2L);

        final Set<String> result = impl.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains("PRJ-100"));
        assertTrue(result.contains("PRJ-200"));
        assertFalse(result.contains("PRJ-300"));
    }

    @Test
    public void testDelete() {
        impl.incrementBy("PRJ-100", 1L);
        impl.incrementBy("PRJ-200", 2L);

        deleteAll();
        assertEquals(0, impl.findAll().size());
    }

    @Test
    public void testIncrementCount() throws Exception {
        assertEquals(1, impl.incrementBy("PRJ-100", 1L).longValue());
        assertEquals(2L, impl.incrementBy("PROJ-200", 2L).longValue());
        assertEquals(4L, impl.incrementBy("PRJ-100", 3L).longValue());
    }
}