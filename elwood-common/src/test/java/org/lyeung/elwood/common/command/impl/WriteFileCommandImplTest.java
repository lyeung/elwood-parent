package org.lyeung.elwood.common.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.lyeung.elwood.common.command.WriteFileCommandException;
import org.lyeung.elwood.common.command.WriteFileCommandParam;
import org.lyeung.elwood.common.test.SlowTest;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 16/12/2015.
 */
@Category(SlowTest.class)
public class WriteFileCommandImplTest {

    private static final String CONTENT = "content";

    private static final String CONTENT_FILENAME = "content.txt";

    private static final String PARENT_DIR = "test-write";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private File parentDir;

    @Before
    public void setUp() throws Exception {
        parentDir = temporaryFolder.newFolder(PARENT_DIR);
    }

    @Test
    public void testExecute() throws IOException {
        final WriteFileCommandParam param = new WriteFileCommandParam();
        param.setContent(CONTENT);
        param.setFile(new File(parentDir, CONTENT_FILENAME));
        final File writtenFile = new WriteFileCommandImpl().execute(param);

        assertNotNull(writtenFile);
        assertEquals(CONTENT, FileUtils.readFileToString(writtenFile));
    }

    @Test
    public void testExecuteFailed() throws IOException {
        expectedException.expect(WriteFileCommandException.class);
        expectedException.expectMessage("unable to write to file=["
                + new File(parentDir, CONTENT_FILENAME).getAbsolutePath() + "]");

        final WriteFileCommandParam param = new WriteFileCommandParam();
        param.setContent(CONTENT);
        boolean success = parentDir.setWritable(false);
        assertTrue(success);
        param.setFile(new File(parentDir, CONTENT_FILENAME));
        new WriteFileCommandImpl().execute(param);
    }
}