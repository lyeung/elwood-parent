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

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.command.ShellCommandParam;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
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
                Arrays.asList(new DefaultEventListener<>(
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