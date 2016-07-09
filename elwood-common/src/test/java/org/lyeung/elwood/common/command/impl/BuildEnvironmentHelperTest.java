/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.common.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
