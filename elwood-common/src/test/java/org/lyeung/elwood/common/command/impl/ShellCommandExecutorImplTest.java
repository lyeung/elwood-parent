package org.lyeung.elwood.common.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.test.SlowTest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lyeung on 13/07/2015.
 */
@Category(value = SlowTest.class)
public class ShellCommandExecutorImplTest {

    @Test
    public void testExecute() throws Exception {

        final ShellCommandParam param = new ShellCommandParamBuilder().command("cat src/test/resources/readme.txt")
                .environmentVars("TEST_VAR=123")
                .directory(".")
                .redirectErrorStream(true)
                .build();
        final Process process = new ShellCommandImpl().execute(param);

        final StringBuilder builder = new StringBuilder();
        final Integer exitStatus = new ShellCommandExecutorImpl(
                Arrays.asList(new ShellCommandExecutorListenerImpl(
                        e -> {
                            try {
                                builder.append(new String(e.getEventData().getData(), EncodingConstants.UTF_8))
                                        .append("\n");
                            } catch (UnsupportedEncodingException ex) {
                                fail(ex.getMessage());
                            }
                        })))
                .execute(process);

        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }

        assertEquals(0, exitStatus.intValue());
        assertEquals(FileUtils.readFileToString(new File("src/test/resources/readme.txt")), builder.toString());
    }}