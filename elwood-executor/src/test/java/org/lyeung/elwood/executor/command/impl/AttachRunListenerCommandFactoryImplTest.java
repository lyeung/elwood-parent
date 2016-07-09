package org.lyeung.elwood.executor.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.executor.command.AddSurefirePluginRunListenerCommandFactory;
import org.lyeung.elwood.executor.command.AttachRunListenerCommand;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class AttachRunListenerCommandFactoryImplTest {

    @Mock
    private AddSurefirePluginRunListenerCommandFactory addRunListenerCommandFactory;

    @Mock
    private AddSurefirePluginRunListenerCommand addRunListenerCommand;

    @Test
    public void testMakeCommand() {
        when(addRunListenerCommandFactory.makeCommand()).thenReturn(addRunListenerCommand);
        final AttachRunListenerCommand command = new AttachRunListenerCommandFactoryImpl(
                addRunListenerCommandFactory).makeCommand();
        assertNotNull(command);
    }
}