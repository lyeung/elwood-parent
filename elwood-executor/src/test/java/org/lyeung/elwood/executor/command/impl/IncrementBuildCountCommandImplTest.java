package org.lyeung.elwood.executor.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.repository.BuildCountRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 29/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class IncrementBuildCountCommandImplTest {

    @Mock
    private BuildCountRepository buildCountRepository;

    private IncrementBuildCountCommandImpl impl;

    @Before
    public void setUp() {
        impl = new IncrementBuildCountCommandImpl(buildCountRepository);
    }

    @Test
    public void testExecute() {
        when(buildCountRepository.incrementBy("PRJ-100", 1L)).thenReturn(2L);

        assertEquals(2L, impl.execute("PRJ-100").longValue());

        verify(buildCountRepository).incrementBy(eq("PRJ-100"), eq(1L));
        verifyNoMoreInteractions(buildCountRepository);
    }
}