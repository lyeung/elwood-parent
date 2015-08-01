package org.lyeung.elwood.common.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.test.SlowTest;

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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
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
