package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by lyeung on 25/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SaveBuildResultMavenStatsCommandImplTest {

    @Mock
    private BuildResultMavenStatsRepository repository;

    private SaveBuildResultMavenStatsCommandImpl impl;


    @Before
    public void setUp() {
        impl = new SaveBuildResultMavenStatsCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        final BuildResultMavenStats value = new BuildResultMavenStats();
        final BuildResultKey key = new BuildResultKey(new BuildKey("PRJ"), 1L);
        value.setKey(key);

        final BuildResultMavenStats result = impl.execute(value);
        assertEquals(value, result);

        verify(repository).save(eq(value));
        verifyNoMoreInteractions(repository);
    }
}