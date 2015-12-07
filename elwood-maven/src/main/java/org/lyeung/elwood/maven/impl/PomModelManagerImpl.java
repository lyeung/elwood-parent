package org.lyeung.elwood.maven.impl;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelBuildingResult;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.lyeung.elwood.maven.PomModelManager;
import org.lyeung.elwood.maven.ReadPomException;
import org.lyeung.elwood.maven.WritePomException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/*
 * Created by lyeung on 13/11/2015.
 */
public class PomModelManagerImpl implements PomModelManager {

    @Override
    public Model readPom(File pomFile) {
        System.setProperty(MavenCli.MULTIMODULE_PROJECT_DIRECTORY, pomFile.getAbsolutePath());

        try {
            return buildModel(pomFile);
        } catch (ModelBuildingException e) {
            throw new ReadPomException("unable to read pom", e, pomFile);
        }
    }

    private Model buildModel(File pomFile) throws ModelBuildingException {
        final ModelBuilder builder = new DefaultModelBuilderFactory().newInstance();
        final ModelBuildingResult result = builder.build(createBuildingRequest(pomFile));

        return result.getEffectiveModel();
    }

    private ModelBuildingRequest createBuildingRequest(File pomFile) {
        final DefaultModelBuildingRequest request = new DefaultModelBuildingRequest();
        request.setProcessPlugins(true);
        request.setPomFile(pomFile);

        return request;
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
