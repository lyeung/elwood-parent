package org.lyeung.elwood.common.command;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.QuickTest;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 17/12/2015.
 */
@Category(QuickTest.class)
public class WriteFileCommandParamBuilderTest {

    private static final String CONTENT = "content";

    @Test
    public void testBuild() {
        final File file = new File("file");

        final WriteFileCommandParam param = new WriteFileCommandParamBuilder()
                .file(file)
                .content(CONTENT)
                .build();

        assertEquals(file, param.getFile());
        assertEquals(CONTENT, param.getContent());
    }
}
