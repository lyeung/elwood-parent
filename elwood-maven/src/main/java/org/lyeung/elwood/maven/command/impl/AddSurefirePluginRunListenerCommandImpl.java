package org.lyeung.elwood.maven.command.impl;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.lyeung.PomModelManager;
import org.lyeung.elwood.maven.ElwoodMavenConstants;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommand;
import org.lyeung.elwood.maven.command.AddSurefirePluginRunListenerCommandParam;

import java.io.File;

/**
 * Created by lyeung on 23/11/2015.
 */
public class AddSurefirePluginRunListenerCommandImpl implements
        AddSurefirePluginRunListenerCommand {

    private final PomModelManager pomModelManager;

    public AddSurefirePluginRunListenerCommandImpl(PomModelManager pomModelManager) {
        this.pomModelManager = pomModelManager;
    }

    @Override
    public String execute(AddSurefirePluginRunListenerCommandParam param) {
        final Model model = pomModelManager.readPom(new File(param.getPomFile()));

        final Plugin plugin = model.getBuild().getPluginsAsMap().get(
                ElwoodMavenConstants.SUREFIRE_PLUGIN);
        final Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();

        final Xpp3Dom props = new Xpp3Dom("properties");
        config.addChild(props);
        props.addChild(pomModelManager.createProperty("property", "runListener",
                ElwoodMavenConstants.RUN_LISTENER_CLASS));

        return pomModelManager.writeModel(model);
    }
}
