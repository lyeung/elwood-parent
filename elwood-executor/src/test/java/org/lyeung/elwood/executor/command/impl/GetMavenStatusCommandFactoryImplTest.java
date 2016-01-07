package org.lyeung.elwood.executor.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.command.GetMavenStatusCommand;
import org.lyeung.elwood.executor.command.MavenStatusRuleMatcherManager;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 3/01/2016.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetMavenStatusCommandFactoryImplTest {

    @Mock
    private MavenStatusRuleMatcherManager matcherManager;

    @Test
    public void testMakeCommand() {
        final GetMavenStatusCommand command = new GetMavenStatusCommandFactoryImpl(
                matcherManager).makeCommand();

        assertNotNull(command);
    }
}