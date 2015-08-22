package org.lyeung.elwood.common.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.QuickTest;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 9/08/2015.
 */
@Category(QuickTest.class)
public class BuildEnvironmentHelperTest {

    @Test
    public void testBuildEnvironment() {
        final String envVars = "M2_HOME=/usr/local/maven JAVA_HOME=/usr/local/jdk";
        final Map<String, String> result = new BuildEnvironmentHelper().buildEnvironment(envVars);

        assertEquals(2, result.size());
        assertEquals("/usr/local/maven", result.get("M2_HOME"));
        assertEquals("/usr/local/jdk", result.get("JAVA_HOME"));
    }

    @Test
    public void testEmptyBuildEnvironment() {
        final Map<String, String> result = new BuildEnvironmentHelper().buildEnvironment("");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullBuildEnvironment() {
        final Map<String, String> result = new BuildEnvironmentHelper().buildEnvironment(null);
        assertTrue(result.isEmpty());
    }
}
