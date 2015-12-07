package org.lyeung.elwood.maven;

import org.apache.maven.model.Model;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.io.File;

/**
 * Created by lyeung on 13/11/2015.
 */
public interface PomModelManager {

    Model readPom(File pomFile);

    String writeModel(Model model);

    Xpp3Dom createProperty(String property, String name, String value);
}
