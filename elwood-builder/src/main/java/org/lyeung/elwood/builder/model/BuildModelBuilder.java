package org.lyeung.elwood.builder.model;

/**
 * Created by lyeung on 9/07/15.
 */
public class BuildModelBuilder {

    private ProjectModel projectModel;

    private String buildCommand;

    private String environmentVars;

    private String workingDirectory;

    public BuildModelBuilder projectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
        return this;
    }

    public BuildModelBuilder buildCommand(String buildCommand) {
        this.buildCommand = buildCommand;
        return this;
    }

    public BuildModelBuilder environmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
        return this;
    }

    public BuildModelBuilder workingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        return this;
    }
    public BuildModel build() {
        BuildModel buildModel = new BuildModel();

        buildModel.setProjectModel(projectModel);
        buildModel.setBuildCommand(buildCommand);
        buildModel.setEnvironmentVars(environmentVars);
        buildModel.setWorkingDirectory(workingDirectory);
        return buildModel;
    }
}
