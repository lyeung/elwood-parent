package org.lyeung.elwood.common.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.WriteFileCommand;
import org.lyeung.elwood.common.test.QuickTest;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
public class WriteFileCommandFactoryImplTest {

    @Test
    public void testMakeCommand() {
        final WriteFileCommand command = new WriteFileCommandFactoryImpl().makeCommand();
        assertNotNull(command);
    }
}
