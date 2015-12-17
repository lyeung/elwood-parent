package org.lyeung.elwood.executor.command;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.Build;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lyeung on 15/12/2015.
 */
@Category(QuickTest.class)
public class AttachRunListenerCommandParamBuilderTest {

    @Test
    public void testBuild() {
        final File file = new File("test");
        final Build build = new Build();

        AttachRunListenerCommandParam param = new AttachRunListenerCommandParamBuilder()
                .outputDir(new File("test"))
                .build(build)
                .build();
        assertEquals(file, param.getOutputDir());
        assertEquals(build, param.getBuild());

        param = new AttachRunListenerCommandParamBuilder()
                .outputDir(null)
                .build(null)
                .build();
        assertNull(param.getOutputDir());
        assertNull(param.getBuild());
    }
}