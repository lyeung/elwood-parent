package org.lyeung.elwood.common.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.MkDirCommandException;
import org.lyeung.elwood.common.command.MkDirCommandParam;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.test.SlowTest;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 19/07/2015.
 */
@Category(SlowTest.class)
public class MkDirCommandImplTest {

    private static final String LOCAL_DIR = "target/mk-dir-command";

    @Before
    public void setUp() throws Exception {
        final File directory = new File(LOCAL_DIR);
        if (directory.exists()) {
            FileUtils.forceDelete(directory);
        }
    }

    @Test
    public void testExecute() throws Exception {
        final MkDirCommandParam param = new MkDirCommandParamBuilder().directory(LOCAL_DIR).build();
        final File directory = new MkDirCommandImpl().execute(param);
        assertTrue(directory.isDirectory());
    }

    @Test(expected = MkDirCommandException.class)
    public void testExecuteExistingDirectory() {
        final MkDirCommandParam param = new MkDirCommandParamBuilder().directory(LOCAL_DIR).build();
        new MkDirCommandImpl().execute(param);
        new MkDirCommandImpl().execute(param);
    }
}