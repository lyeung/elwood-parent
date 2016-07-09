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

import com.github.lyeung.common.test.SlowTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 12/07/2015.
 */
@Category(value = SlowTest.class)
public class ShellCommandImplTest {

    private ShellCommandImpl command;

    @Before
    public void setUp() {
        command = new ShellCommandImpl();
    }

    @Test
    public void testExecute() throws IOException, InterruptedException {

        StringBuilder builder = new StringBuilder();

        final ShellCommandParam param = new ShellCommandParamBuilder().command("echo hello")
                .environmentVars("TEST_VAR=123")
                .directory(".")
                .redirectErrorStream(true)
                .build();
        final Process process = command.execute(param);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
        }

        assertEquals(0, process.waitFor());
        assertEquals("hello", builder.toString());
    }

    @Test(expected = ShellCommandException.class)
    public void testInvalidWorkingDirectory() {
        command.execute(new ShellCommandParamBuilder()
                .command("echo hello")
                .directory("nodir").build());
    }
}
