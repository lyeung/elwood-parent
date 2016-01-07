package org.lyeung.elwood.executor.command;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 4/01/2016.
 */
@Category(QuickTest.class)
public class GetMavenStatusCommandParamBuilderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testBuild() throws IOException {
        final File dir = temporaryFolder.newFolder("test123");
        final GetMavenStatusCommandParam param = new GetMavenStatusCommandParamBuilder()
                .buildResultKey(new BuildResultKey(new BuildKey("PRJ"), 10))
                .checkedOutDir(dir)
                .build();

        assertNotNull(param);
        assertEquals(dir.getAbsolutePath(), param.getCheckedOutDir().getAbsolutePath());
        assertEquals("PRJ-10", param.getBuildResultKey().toStringValue());
    }
}