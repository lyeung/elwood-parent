package org.lyeung.elwood.maven.command.impl;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.lyeung.elwood.maven.ElwoodMavenConstants;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParam;

import java.io.File;

/**
 * Created by lyeung on 23/11/2015.
 */
public class AddSurefirePluginRunListenerCommandImpl implements
        AddSurefirePluginRunListenerCommand {

    private static final String CONFIGURATION = "configuration";

    private static final String PROPERTIES = "properties";

    private static final String PROPERTY = "property";

    private static final String NAME = "name";

    private static final String VALUE = "value";

    private static final String LISTENER = "listener";

    private final PomModelManager pomModelManager;

    public AddSurefirePluginRunListenerCommandImpl(PomModelManager pomModelManager) {
        this.pomModelManager = pomModelManager;
    }

    @Override
    public String execute(AddSurefirePluginRunListenerCommandParam param) {
        final Model model = pomModelManager.readPom(new File(param.getPomFile()));

        Plugin surefirePlugin = getPlugin(model, ElwoodMavenConstants.SUREFIRE_PLUGIN);
        if (surefirePlugin == null) {
            surefirePlugin = createPlugin(model, ElwoodMavenConstants.MAVEN_PLUGINS_GROUP_ID,
                    ElwoodMavenConstants.SUREFIRE_PLUGIN_ARTIFACT_ID);
            model.getBuild().addPlugin(surefirePlugin);
        }
        addPluginProperties(surefirePlugin);

        final Plugin failsafePlugin = getPlugin(model,
                ElwoodMavenConstants.FAILSAFE_PLUGIN);
        if (failsafePlugin != null) {
            addPluginProperties(failsafePlugin);
        }

        return pomModelManager.writeModel(model);
    }

    private Plugin createPlugin(Model model, String groupId, String artifactId) {
        Plugin plugin = getPlugin(model, groupId + ":" + artifactId);
        if (plugin == null) {
            plugin = new Plugin();
            plugin.setArtifactId(groupId);
            plugin.setArtifactId(artifactId);
        }

        return plugin;
    }

    private Plugin addPluginProperties(Plugin plugin) {
        Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
        if (config == null) {
            config = new Xpp3Dom(CONFIGURATION);
            plugin.setConfiguration(config);
        }

        config = (Xpp3Dom) plugin.getConfiguration();

        Xpp3Dom propertiesDom = findChild(config.getChildren(), PROPERTIES);
        if (propertiesDom == null) {
            propertiesDom = new Xpp3Dom(PROPERTIES);
            config.addChild(propertiesDom);
        }


        Xpp3Dom propertyDom = findChild(propertiesDom.getChildren(), PROPERTY);
        if (propertyDom == null) {
            propertyDom = new Xpp3Dom(PROPERTY);
            propertiesDom.addChild(propertyDom);
        }

        Xpp3Dom nameDom = findChild(propertyDom.getChildren(), NAME);
        if (nameDom == null) {
            nameDom = new Xpp3Dom(NAME);
            nameDom.setValue(LISTENER);
            propertyDom.addChild(nameDom);
        }

        Xpp3Dom valueDom = findChild(propertyDom.getChildren(), VALUE);
        if (valueDom == null) {
            valueDom = new Xpp3Dom(VALUE);
            valueDom.setValue(ElwoodMavenConstants.RUN_LISTENER_CLASS);
            propertyDom.addChild(valueDom);
        }

        return plugin;
    }

    private Xpp3Dom findChild(Xpp3Dom[] children, String name) {
        for (Xpp3Dom child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }

        return null;
    }

    private Plugin getPlugin(Model model, String pluginName) {
        return model.getBuild().getPluginsAsMap().get(pluginName);
    }
}
