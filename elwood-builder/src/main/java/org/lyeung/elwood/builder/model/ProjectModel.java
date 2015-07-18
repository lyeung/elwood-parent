package org.lyeung.elwood.builder.model;

/**
 * Created by lyeung on 9/07/15.
 */
public class ProjectModel {

    private String name;

    private String description;

    private String buildFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildFile() {
        return buildFile;
    }

    public void setBuildFile(String buildFile) {
        this.buildFile = buildFile;
    }

}
