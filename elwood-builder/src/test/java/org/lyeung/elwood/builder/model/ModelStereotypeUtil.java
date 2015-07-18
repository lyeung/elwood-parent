package org.lyeung.elwood.builder.model;

/**
 * Created by lyeung on 11/07/2015.
 */
public final class ModelStereotypeUtil {

    private ModelStereotypeUtil() {
        // do-nothing
    }

    public static BuildModel createBuildModel(String command, ProjectModel projectModel) {
        return new BuildModelBuilder()
                .buildCommand(command)
                .environmentVars("TEST_VAR=" + projectModel.getBuildFile())
                .projectModel(projectModel)
                .workingDirectory(".")
                .build();
    }

    public static ProjectModel createProjectModel() {
        return new ProjectModelBuilder()
                .buildFile("pom.xml")
                .description("test maven project")
                .name("test maven")
                .build();
    }
}
