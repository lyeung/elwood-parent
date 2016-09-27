package org.lyeung.elwood.executor.command.impl;

import com.github.lyeung.common.test.QuickTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParam;
import org.lyeung.elwood.executor.command.AttachRunListenerCommandParamBuilder;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParam;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 15/12/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class AttachRunListenerCommandImplTest {

    public static final String CONTENT = "<content/>";
    @Mock
    private AddSurefirePluginRunListenerCommand addSurefirePluginRunListenerCommand;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testExecute() throws IOException {
        final File tempDir = temporaryFolder.newFolder("temp");
        final AttachRunListenerCommandParam param = new AttachRunListenerCommandParamBuilder()
                .build(createBuild("mvn clean package"))
                .outputDir(tempDir)
                .build();

        when(addSurefirePluginRunListenerCommand.execute(
                any(AddSurefirePluginRunListenerCommandParam.class)))
                .thenReturn(CONTENT);

        final String content = new AttachRunListenerCommandImpl(addSurefirePluginRunListenerCommand)
                .execute(param);

        assertEquals(CONTENT, content);

        verify(addSurefirePluginRunListenerCommand).execute(argThat(
                new ArgumentMatcher<AddSurefirePluginRunListenerCommandParam>() {
                    @Override
                    public boolean matches(Object argument) {
                        final AddSurefirePluginRunListenerCommandParam param =
                                (AddSurefirePluginRunListenerCommandParam) argument;
                        return param.getPomFile().equals(new File(tempDir, "pom.xml")
                                .getAbsolutePath());
                    }
                }));
        verifyNoMoreInteractions(addSurefirePluginRunListenerCommand);
    }

    @Test
    public void testExecuteSimpleFileParam() throws IOException {
        final File tempDir = temporaryFolder.newFolder("temp");
        final AttachRunListenerCommandParam param = new AttachRunListenerCommandParamBuilder()
                .build(createBuild("mvn clean package -f updated-pom.xml"))
                .outputDir(tempDir)
                .build();

        when(addSurefirePluginRunListenerCommand.execute(
                any(AddSurefirePluginRunListenerCommandParam.class)))
                .thenReturn(CONTENT);

        final String content = new AttachRunListenerCommandImpl(addSurefirePluginRunListenerCommand)
                .execute(param);

        assertEquals(CONTENT, content);

        verify(addSurefirePluginRunListenerCommand).execute(argThat(
                new ArgumentMatcher<AddSurefirePluginRunListenerCommandParam>() {
                    @Override
                    public boolean matches(Object argument) {
                        final AddSurefirePluginRunListenerCommandParam param =
                                (AddSurefirePluginRunListenerCommandParam) argument;
                        return param.getPomFile().equals(new File(tempDir, "updated-pom.xml")
                                .getAbsolutePath());
                    }
                }));
        verifyNoMoreInteractions(addSurefirePluginRunListenerCommand);
    }

    @Test
    public void testExecuteGnuFileParam() throws IOException {
        final File tempDir = temporaryFolder.newFolder("temp");
        final AttachRunListenerCommandParam param = new AttachRunListenerCommandParamBuilder()
                .build(createBuild("mvn clean package --file updated-pom.xml"))
                .outputDir(tempDir)
                .build();

        when(addSurefirePluginRunListenerCommand.execute(
                any(AddSurefirePluginRunListenerCommandParam.class)))
                .thenReturn(CONTENT);

        final String content = new AttachRunListenerCommandImpl(addSurefirePluginRunListenerCommand)
                .execute(param);

        assertEquals(CONTENT, content);

        verify(addSurefirePluginRunListenerCommand).execute(argThat(
                new ArgumentMatcher<AddSurefirePluginRunListenerCommandParam>() {
                    @Override
                    public boolean matches(Object argument) {
                        final AddSurefirePluginRunListenerCommandParam param =
                                (AddSurefirePluginRunListenerCommandParam) argument;
                        return param.getPomFile().equals(new File(tempDir, "updated-pom.xml")
                                .getAbsolutePath());
                    }
                }));
        verifyNoMoreInteractions(addSurefirePluginRunListenerCommand);
    }

    private Build createBuild(String command) {
        final Build build = new Build();
        build.setBuildCommand(command);

        return build;
    }
}