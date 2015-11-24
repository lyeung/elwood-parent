package org.lyeung.elwood.maven.impl;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.lyeung.elwood.ReadPomException;
import org.lyeung.elwood.WritePomException;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.maven.ElwoodMavenConstants;

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
 * Created by lyeung on 13/11/2015.
 */
@Category(SlowTest.class)
public class PomModelManagerImplTest {

    private static final String PROJECT_NAME = "test-sample-multi-module";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String PATH = "src/test/resources/test-sample-multi-module/pom.xml";

    private static final String INVALID_PATH = System.getProperty("java.io.tmpdir")
            + "/" + "invalid";

    private File updatedPomFile;

    private PomModelManagerImpl impl;

    @Before
    public void setUp() throws IOException {
        final File folder = temporaryFolder.newFolder("temp");
        updatedPomFile = new File(folder, "updated-pom.xml");
        impl = new PomModelManagerImpl();
    }

    private Model readPom(String path) {
        return impl.readPom(new File(path));
    }

    @Test
    public void testReadPom() throws Exception {
        final Model model = readPom(PATH);

        assertEquals(PROJECT_NAME, model.getName());
        final Plugin plugin = model.getBuild().getPluginsAsMap().get(
                ElwoodMavenConstants.SUREFIRE_PLUGIN);
        assertNotNull(plugin);

        final Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
        final Xpp3Dom props = config.getChild("properties");
        assertNull(props);
    }

    @Test(expected = ReadPomException.class)
    public void testReadInvalidFile() {
        readPom(INVALID_PATH);
    }

    @Test
    public void testWriteModel() throws Exception {
        final Model model = readPom(PATH);
        final Plugin plugin = model.getBuild().getPluginsAsMap().get(
                ElwoodMavenConstants.SUREFIRE_PLUGIN);
        final Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
        assertNull(config.getChild("properties"));

        final Xpp3Dom props = new Xpp3Dom("properties");
        config.addChild(props);
        props.addChild(impl.createProperty("property", "runListener",
                ElwoodMavenConstants.RUN_LISTENER_CLASS));

        final String content = new PomModelManagerImpl().writeModel(model);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(updatedPomFile))) {
            writer.write(content, 0, content.length());
        }

        assertUpdatedPom(updatedPomFile);
    }

    // TODO: find a better way to test writing invalid file
    @Ignore
    @Test(expected = WritePomException.class)
    public void testWriteInvalidFile() {
        // do-nothing
    }

    private void assertUpdatedPom(File path) {
        final Model model = readPom(path.getAbsolutePath());

        assertEquals(PROJECT_NAME, model.getName());
        final Plugin plugin = model.getBuild().getPluginsAsMap().get(
                ElwoodMavenConstants.SUREFIRE_PLUGIN);
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
