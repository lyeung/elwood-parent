package org.lyeung.elwood.builder.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.builder.model.BuildModel;
import org.lyeung.elwood.builder.model.ModelStereotypeUtil;
import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.command.MkDirCommand;
import org.lyeung.elwood.common.command.MkDirCommandParam;
import org.lyeung.elwood.common.command.MkDirCommandParamBuilder;
import org.lyeung.elwood.common.command.ShellCommandParamBuilder;
import org.lyeung.elwood.common.command.event.impl.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.command.impl.MkDirCommandImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandExecutorImpl;
import org.lyeung.elwood.common.command.impl.ShellCommandImpl;
import org.lyeung.elwood.common.event.impl.DefaultEventListener;
import org.lyeung.elwood.common.test.SlowTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lyeung on 12/07/2015.
 */
@Category(SlowTest.class)
public class MavenBuildIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenBuildIntegrationTest.class);

    private static final String LOCAL_DIR = "target/test-sample-artifact";

    @Test
    public void testExecute() throws IOException {
        final File localDir = new MkDirCommandImpl().execute(new MkDirCommandParamBuilder()
                .directory(LOCAL_DIR)
                .build());
        FileUtils.copyDirectory(new File("src/test/resources/test-sample-artifact"), localDir);

        final BuildModel buildModel = ModelStereotypeUtil.createBuildModel("mvn clean package",
                ModelStereotypeUtil.createProjectModel());
        buildModel.setWorkingDirectory("target/test-sample-artifact");
        final Process process = new ProcessBuilderCommandImpl(new ShellCommandImpl(), new ShellCommandParamBuilder())
                .execute(buildModel);

        final Integer exitStatus = new ProjectBuilderCommandImpl(new ShellCommandExecutorImpl(Arrays.asList(
                createShellCommandExecutorListener()))).execute(process);
        assertEquals(0, exitStatus.intValue());
    }

    private DefaultEventListener<ShellCommandExecutorEventData> createShellCommandExecutorListener() {
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
