package org.lyeung.elwood.common.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.MkDirCommand;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
public class MkDirCommandFactoryImplTest {

    @Test
    public void testMakeCommand() {
        final MkDirCommand mkDirCommand = new MkDirCommandFactoryImpl().makeCommand();
        assertNotNull(mkDirCommand);
    }
}