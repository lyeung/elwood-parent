package org.lyeung.elwood.maven.command.impl;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
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

        addElwoodRunListenerArtifactDependencyManagement(model);
        addSurefirePlugin(model);
        addFailsafePluginProperties(model);

        return pomModelManager.writeModel(model);
    }

    private void addFailsafePluginProperties(Model model) {
        final Plugin failsafePlugin = getPlugin(model.getBuild(),
                ElwoodMavenConstants.FAILSAFE_PLUGIN);
        if (failsafePlugin != null) {
            addPluginProperties(failsafePlugin);
        }
    }

    private void addSurefirePlugin(Model model) {
        final Build build = getBuild(model);
        Plugin surefirePlugin = getPlugin(build, ElwoodMavenConstants.SUREFIRE_PLUGIN);
        if (surefirePlugin == null) {
            surefirePlugin = createPlugin(build, ElwoodMavenConstants.MAVEN_PLUGINS_GROUP_ID,
                    ElwoodMavenConstants.SUREFIRE_PLUGIN_ARTIFACT_ID);
            build.addPlugin(surefirePlugin);
        }

        addPluginProperties(surefirePlugin);
    }

    private void addElwoodRunListenerArtifactDependencyManagement(Model model) {
        boolean found = false;
        final Dependency elwoodRunListenerArtifact = createElwoodRunListenerArtifact();
        for (Dependency dependency : model.getDependencies()) {
            if (isElwoodRunListenerArtifact(dependency, elwoodRunListenerArtifact)) {
                found = true;
                break;
            }
        }

        if (!found) {
            model.addDependency(elwoodRunListenerArtifact);
        }
    }

    private Dependency createElwoodRunListenerArtifact() {
        Dependency dependency = new Dependency();
        dependency.setGroupId(ElwoodMavenConstants.ELWOOD_RUN_LISTENER_GROUP_ID);
        dependency.setArtifactId(ElwoodMavenConstants.ELWOOD_RUN_LISTENER_ARTIFACT_ID);
        dependency.setVersion(ElwoodMavenConstants.ELWOOD_RUN_LISTENER_VERSION);

        return dependency;
    }

    private boolean isElwoodRunListenerArtifact(Dependency dependency, Dependency elwoodArtifact) {
        return dependency.getGroupId().equals(elwoodArtifact.getGroupId())
                && dependency.getArtifactId().equals(elwoodArtifact.getArtifactId())
                && dependency.getVersion().equals(elwoodArtifact.getVersion());
    }

    private Build getBuild(Model model) {
        if (model.getBuild() == null) {
            Build build = new Build();
            model.setBuild(build);
        }

        return model.getBuild();
    }

    private Plugin createPlugin(Build build, String groupId, String artifactId) {
        Plugin plugin = getPlugin(build, groupId + ":" + artifactId);
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

    private Plugin getPlugin(Build build, String pluginName) {
        return build.getPluginsAsMap().get(pluginName);
    }
}
