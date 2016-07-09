package org.lyeung.elwood.builder.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.command.ShellCommand;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class ProcessBuilderCommandFactoryImplTest {

    @Mock
    private ShellCommand shellCommand;

    @Test
    public void testMakeCommand() {
        final ProcessBuilderCommandImpl command = new ProcessBuilderCommandFactoryImpl()
                .makeCommand(shellCommand);
        assertNotNull(command);
    }
}