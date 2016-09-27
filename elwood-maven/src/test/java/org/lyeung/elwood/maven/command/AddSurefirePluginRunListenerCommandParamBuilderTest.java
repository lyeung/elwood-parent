package org.lyeung.elwood.maven.command;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lyeung on 15/12/2015.
 */
@Category(QuickTest.class)
public class AddSurefirePluginRunListenerCommandParamBuilderTest {

    @Test
    public void testBuild() {
        AddSurefirePluginRunListenerCommandParam param =
                new AddSurefirePluginRunListenerCommandParamBuilder()
                        .pomFile("pom.xml")
                        .build();
        assertEquals("pom.xml", param.getPomFile());

        param = new AddSurefirePluginRunListenerCommandParamBuilder()
                        .pomFile(null)
                        .build();
        assertNull(param.getPomFile());
    }
}