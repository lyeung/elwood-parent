package org.lyeung.elwood.maven.command.impl;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.notification.RunListener;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void testExecute() throws IOException {
        final String content = impl.execute(new AddSurefirePluginRunListenerCommandParamBuilder()
                .pomFile("src/test/resources/test-simple/pom.xml")
                .runListenerClassNames(
                        Collections.singletonList(RunListener.class.getCanonicalName()))
                .build());

        final File folder = temporaryFolder.newFolder("temp");
        final File updatedPom = new File(folder, "updated-pom.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(updatedPom))) {
            writer.write(content, 0, content.length());
        }

        final Model model = pomModelManager.readPom(updatedPom);
        assertEquals("test-simple", model.getName());
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