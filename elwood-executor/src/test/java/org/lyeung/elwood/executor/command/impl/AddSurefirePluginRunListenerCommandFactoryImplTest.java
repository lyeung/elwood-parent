package org.lyeung.elwood.executor.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class AddSurefirePluginRunListenerCommandFactoryImplTest {

    @Mock
    private PomModelManager pomModelManager;

    @Test
    public void testMakeCommand() {
        final AddSurefirePluginRunListenerCommand command =
                new AddSurefirePluginRunListenerCommandFactoryImpl(pomModelManager).makeCommand();
        assertNotNull(command);
    }
}