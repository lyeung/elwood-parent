package org.lyeung.elwood.vcs.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.CloneCommandParamBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by lyeung on 16/07/2015.
 */
@Category(SlowTest.class)
public class GitCloneCommandImplTest {

    private static final String REMOTE_URI = "https://github.com/lyeung/ng-sample.git";

    private static final String LOCAL_DIR = "target/ng-sample";

    private File localDir;

    @Before
    public void setUp() throws Exception {
        localDir = new File(LOCAL_DIR);
        if (localDir.isDirectory() && localDir.exists()) {
            FileUtils.forceDelete(localDir);
        }

        localDir.mkdir();
    }

    @Test
    public void testExecute() throws Exception {
        assertTrue(localDir.list().length == 0);

        StringBuilder builder = new StringBuilder();

        final CloneCommandParam param = new CloneCommandParamBuilder()
                .localDirectory(LOCAL_DIR)
                .remoteUri(REMOTE_URI)
                .build();
        final File directory = new GitCloneCommandImpl(Arrays.asList(new DefaultEventListener<>(
                e -> {
                    try {
                        builder.append(new String(e.getEventData().getData(), EncodingConstants.UTF_8));
                    } catch (UnsupportedEncodingException e1) {
                        fail("unsupported encoding");
                    }
                })))
                .execute(param);

        assertTrue(directory.list().length > 0);

        final String result = builder.toString();
        assertTrue(result.contains("remote: Counting objects: 61"));
        assertTrue(result.contains("Updating references:    100% (1/1)"));
    }
}
