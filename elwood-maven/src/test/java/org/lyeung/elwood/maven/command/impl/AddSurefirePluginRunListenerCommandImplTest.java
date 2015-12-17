package org.lyeung.elwood.maven.command.impl;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.maven.ElwoodMavenConstants;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParamBuilder;
import org.lyeung.elwood.maven.impl.PomModelManagerImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lyeung on 23/11/2015.
 */
@Category(SlowTest.class)
public class AddSurefirePluginRunListenerCommandImplTest {

    private AddSurefirePluginRunListenerCommandImpl impl;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private PomModelManager pomModelManager;

    @Before
    public void setUp() {
        pomModelManager = new PomModelManagerImpl();
        impl = new AddSurefirePluginRunListenerCommandImpl(pomModelManager);
    }

    @Test
    public void testSimpleExecute() throws IOException {
        final String content = impl.execute(new AddSurefirePluginRunListenerCommandParamBuilder()
                .pomFile("src/test/resources/test-simple/pom.xml")
                .build());

        final File folder = temporaryFolder.newFolder("temp");
        final File updatedPom = new File(folder, "updated-pom.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(updatedPom))) {
            writer.write(content, 0, content.length());
        }

        final Model model = pomModelManager.readPom(updatedPom);
        assertEquals("test-simple", model.getName());

        assertSurefirePlugin(model);
        assertNull(getPlugin(model, ElwoodMavenConstants.FAILSAFE_PLUGIN));
    }

    @Test
    public void testSimpleExecuteWithSurefire() throws IOException {
        final String content = impl.execute(new AddSurefirePluginRunListenerCommandParamBuilder()
                .pomFile("src/test/resources/test-simple/pom-with-surefire.xml")
                .build());

        final File folder = temporaryFolder.newFolder("temp");
        final File updatedPom = new File(folder, "updated-pom-with-surefire.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(updatedPom))) {
            writer.write(content, 0, content.length());
        }

        final Model model = pomModelManager.readPom(updatedPom);
        assertEquals("test-simple", model.getName());

        assertSurefirePlugin(model);
        assertNull(getPlugin(model, ElwoodMavenConstants.FAILSAFE_PLUGIN));

        assertEquals(FileUtils.readFileToString(new File(
                        "src/test/resources/test-simple/expected-updated-pom-with-surefire.xml")),
                FileUtils.readFileToString(new File(folder,
                        "updated-pom-with-surefire.xml")));
    }

    @Test
    public void testSimpleFailsafeExecute() throws IOException {
        final String content = impl.execute(new AddSurefirePluginRunListenerCommandParamBuilder()
                .pomFile("src/test/resources/test-simple-failsafe/pom.xml")
                .build());

        final File folder = temporaryFolder.newFolder("temp");
        final File updatedPom = new File(folder, "updated-pom.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(updatedPom))) {
            writer.write(content, 0, content.length());
        }

        final Model model = pomModelManager.readPom(updatedPom);
        assertEquals("test-simple-failsafe", model.getName());

        assertSurefirePlugin(model);
        assertFailsafePlugin(model);
    }

    private Plugin getPlugin(Model model, String pluginName) {
        return model.getBuild().getPluginsAsMap().get(pluginName);
    }

    private void assertSurefirePlugin(Model model) {
        final Plugin plugin = getPlugin(model, ElwoodMavenConstants.SUREFIRE_PLUGIN);
        assertNotNull(plugin);

        final Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
        final Xpp3Dom props = config.getChild("properties");
        List<Xpp3Dom> propList = Arrays.asList(props.getChildren("property"));
        final List<Xpp3Dom> values = propList.stream()
                .flatMap(p -> Arrays.asList(p.getChildren()).stream()
                        .filter(c -> c.getName().equals("value")))
                .collect(Collectors.toList());

        assertEquals(1, values.size());
        final Xpp3Dom dom = values.get(0);
        assertEquals("value", dom.getName());
        assertEquals(ElwoodMavenConstants.RUN_LISTENER_CLASS, dom.getValue());
    }

    private void assertFailsafePlugin(Model model) {
        final Plugin plugin = getPlugin(model, ElwoodMavenConstants.FAILSAFE_PLUGIN);
        assertNotNull(plugin);

        final Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
        final Xpp3Dom props = config.getChild("properties");
        List<Xpp3Dom> propList = Arrays.asList(props.getChildren("property"));
        final List<Xpp3Dom> values = propList.stream()
                .flatMap(p -> Arrays.asList(p.getChildren()).stream()
                        .filter(c -> c.getName().equals("value")))
                .collect(Collectors.toList());

        assertEquals(1, values.size());
        final Xpp3Dom dom = values.get(0);
        assertEquals("value", dom.getName());
        assertEquals(ElwoodMavenConstants.RUN_LISTENER_CLASS, dom.getValue());
    }
}