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