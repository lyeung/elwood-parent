package org.lyeung.elwood.maven.impl;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.ReadPomException;
import org.lyeung.elwood.maven.WritePomException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

/*
 * Created by lyeung on 13/11/2015.
 */
public class PomModelManagerImpl implements PomModelManager {

    @Override
    public Model readPom(File pomFile) {

        try {
            return buildModel(pomFile);
        } catch (IOException | XmlPullParserException e) {
            throw new ReadPomException("unable to read pom", e, pomFile);
        }
    }

    private Model buildModel(File pomFile) throws IOException, XmlPullParserException {
        return new MavenXpp3Reader().read(new FileReader(pomFile));
    }

    @Override
    public String writeModel(Model model) {
        final MavenXpp3Writer xppWriter = new MavenXpp3Writer();
        final StringWriter writer = new StringWriter();

        try {
            xppWriter.write(writer, model);
            return writer.getBuffer().toString();
        } catch (IOException e) {
            throw new WritePomException("unable to write pom", e, model);
        }
    }

    @Override
    public Xpp3Dom createProperty(String property, String name, String value) {
        final Xpp3Dom dom = new Xpp3Dom(property);
        dom.addChild(createNewXpp3Dom("name", name));
        dom.addChild(createNewXpp3Dom("value", value));
        return dom;
    }

    private Xpp3Dom createNewXpp3Dom(String tag, String value) {
        final Xpp3Dom dom = new Xpp3Dom(tag);
        dom.setValue(value);
        return dom;
    }
}
