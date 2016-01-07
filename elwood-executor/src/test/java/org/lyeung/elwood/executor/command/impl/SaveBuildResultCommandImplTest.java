package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
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
public class SaveBuildResultCommandImplTest {

    @Mock
    private BuildResultRepository repository;

    private SaveBuildResultCommandImpl impl;

    @Before
    public void setUp() {
        impl = new SaveBuildResultCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        final BuildResult buildResult = new BuildResult();
        final BuildResult result = impl.execute(buildResult);
        assertEquals(buildResult, result);

        verify(repository).save(eq(buildResult));
        verifyNoMoreInteractions(repository);
    }
}