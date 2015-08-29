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

package org.lyeung.elwood.vcs.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.CloneCommandParamBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by lyeung on 16/07/2015.
 */
@Category(SlowTest.class)
public class GitCloneCommandImplTest {

    private static final String REMOTE_URI = "https://github.com/lyeung/ng-sample.git";

    private static final String LOCAL_DIR = "target/ng-sample";

    @Before
    public void setUp() throws Exception {
        final File localDir = new File(LOCAL_DIR);
        if (localDir.isDirectory() && localDir.exists()) {
            FileUtils.forceDelete(localDir);
        }

        assertTrue(localDir.mkdir());
        assertTrue(localDir.list().length == 0);
    }

    @Test
    public void testExecute() throws Exception {
        StringBuilder builder = new StringBuilder();

        final CloneCommandParam param = new CloneCommandParamBuilder()
                .localDirectory(LOCAL_DIR)
                .remoteUri(REMOTE_URI)
                .authenticationType(CloneCommandParam.AuthenticationType.NONE)
                .build();
        final File directory = new GitCloneCommandImpl(Collections.singletonList(new DefaultEventListener<>(
                e -> {
                    try {
                        builder.append(new String(e.getEventData().getData(), EncodingConstants.UTF_8));
                    } catch (UnsupportedEncodingException e1) {
                        fail("unsupported encoding");
                    }
                })))
                .execute(param);

        assertTrue(directory.list().length > 0);

        final String result = builder.toString();
        assertTrue(result.contains("remote: Counting objects: 61"));
        assertTrue(result.contains("Updating references:    100% (1/1)"));
    }

    @Test
    public void testExecutePublicKeyPassphraseAuthentication() {
        StringBuilder builder = new StringBuilder();

        final CloneCommandParam param = new CloneCommandParamBuilder()
                .localDirectory(LOCAL_DIR)
                .remoteUri("git@bitbucket.org:lyeung/elwood-parent.git")
                .authenticationType(CloneCommandParam.AuthenticationType.PUBLIC_KEY_PASSPHRASE)
                .identityKey("src/test/resources/ssh/id_rsa-elwood-project")
                .passphrase(null)
                .build();
        final File directory = new GitCloneCommandImpl(Collections.singletonList(new DefaultEventListener<>(
                e -> {
                    try {
                        builder.append(new String(e.getEventData().getData(), EncodingConstants.UTF_8));
                    } catch (UnsupportedEncodingException e1) {
                        fail("unsupported encoding");
                    }
                })))
                .execute(param);

        assertTrue(directory.list().length > 0);

        final String result = builder.toString();
        System.out.println(result);
        assertTrue(result.contains("remote: Counting objects:"));
        assertTrue(result.contains("Updating references:    100%"));
    }
}
