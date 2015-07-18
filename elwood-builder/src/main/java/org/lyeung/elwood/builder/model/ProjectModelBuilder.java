package org.lyeung.elwood.builder.model;

/**
 * Created by lyeung on 9/07/15.
 */
public class ProjectModelBuilder {

    private String name;

    private String description;

    private String buildFile;

    public ProjectModelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectModelBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectModelBuilder buildFile(String buildFile) {
        this.buildFile = buildFile;
        return this;
    }

    public ProjectModel build() {
        ProjectModel model = new ProjectModel();

        model.setName(name);
        model.setDescription(description);
        model.setBuildFile(buildFile);
        return model;
    }
}
