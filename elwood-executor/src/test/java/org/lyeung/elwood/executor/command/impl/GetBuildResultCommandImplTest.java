package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 25/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetBuildResultCommandImplTest {

    @Mock
    private BuildResultRepository repository;

    private GetBuildResultCommandImpl impl;

    @Before
    public void setUp() {
        impl = new GetBuildResultCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        final BuildResultKey buildResultKey = new BuildResultKey(new BuildKey("PRJ"), 1L);
        final BuildResult buildResult = new BuildResult();
        buildResult.setKey(buildResultKey);

        final Optional<BuildResult> optionalResult = Optional.of(buildResult);
        when(repository.getOne(buildResultKey)).thenReturn(optionalResult);

        final Optional<BuildResult> result = impl.execute(buildResultKey);
        assertTrue(result.isPresent());
        assertEquals(result.get(), optionalResult.get());

        verify(repository).getOne(buildResultKey);
        verifyNoMoreInteractions(repository);
    }
}