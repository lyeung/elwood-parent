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

package org.lyeung.elwood.builder.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.builder.model.ModelStereotypeUtil;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.command.event.impl.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.command.impl.MkDirCommandImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandExecutorImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandImpl;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParamBuilder;
import org.lyeung.elwood.maven.command.impl.AddSurefirePluginRunListenerCommandImpl;
import org.lyeung.elwood.maven.impl.PomModelManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lyeung on 12/07/2015.
 */
@Category(SlowTest.class)
public class MavenBuildIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenBuildIntegrationTest.class);

    private static final String LOCAL_DIR = "target/test-sample-artifact";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        final File localDir = new File(LOCAL_DIR);
        if (localDir.isDirectory() && localDir.exists()) {
            FileUtils.forceDelete(localDir);
        }
    }

    @Test
    public void testExecute() throws IOException {
        final File localDir = new MkDirCommandImpl().execute(new MkDirCommandParamBuilder()
                .directory(LOCAL_DIR)
                .build());
        FileUtils.copyDirectory(new File("src/test/resources/test-sample-artifact"), localDir);

        final String content = new AddSurefirePluginRunListenerCommandImpl(
                new PomModelManagerImpl())
                .execute(new AddSurefirePluginRunListenerCommandParamBuilder()
                    .pomFile(new File(localDir, "pom.xml").getAbsolutePath())
                    .build());

        final File updatedPom = new File(localDir, "pom.xml.elwood");

        final boolean created = updatedPom.createNewFile();
        if (!created) {
            fail("unable to update " + updatedPom.getAbsolutePath());
        }

        FileUtils.write(updatedPom, content);

        final BuildModel buildModel = ModelStereotypeUtil.createBuildModel(
                "mvn -f pom.xml.elwood clean package",
                ModelStereotypeUtil.createProjectModel());
        buildModel.setWorkingDirectory("target/test-sample-artifact");
        final Process process = new ProcessBuilderCommandImpl(new ShellCommandImpl())
                .execute(buildModel);

        final Integer exitStatus = new ProjectBuilderCommandImpl(new ShellCommandExecutorImpl(
                Collections.singletonList(createShellCommandExecutorListener()))).execute(process);
        assertEquals(0, exitStatus.intValue());
    }

    private DefaultEventListener<ShellCommandExecutorEventData>
        createShellCommandExecutorListener() {

        return new DefaultEventListener<>(e -> {
            try {
                LOGGER.debug(new String(e.getEventData().getData(), EncodingConstants.UTF_8));
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error("unsupported encoding", ex);
                fail("unsupported encoding");
            }
        });
    }
}
