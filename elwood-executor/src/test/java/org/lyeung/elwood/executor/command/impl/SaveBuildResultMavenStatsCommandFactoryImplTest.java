package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.executor.command.SaveBuildResultMavenStatsCommand;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 26/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SaveBuildResultMavenStatsCommandFactoryImplTest {

    @Mock
    private BuildResultMavenStatsRepository repository;

    private SaveBuildResultMavenStatsCommandFactoryImpl impl;


    @Before
    public void setUp() {
        impl = new SaveBuildResultMavenStatsCommandFactoryImpl(repository);
    }

    @Test
    public void testMkCommand() {
        final SaveBuildResultMavenStatsCommand result = impl.makeCommand();
        assertNotNull(result);
    }
}