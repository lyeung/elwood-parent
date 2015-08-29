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

    private BuildCountRepositoryImpl impl;

    @Before
    public void setUp() {
        final RedisTemplate<String, Long> template = redisCountTemplate();
        impl = new BuildCountRepositoryImpl("test:buildCount", template);
        deleteAll();
    }

    private void deleteAll() {
        impl.delete(impl.findAll().stream().collect(Collectors.toList()));
    }

    @Test
    public void getDomainKey() {
        assertEquals("test:buildCount", impl.getDomainKey());
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