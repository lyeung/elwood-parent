package org.lyeung.elwood.web.model;

/**
 * Created by lyeung on 3/08/2015.
 */
public class Article {

    private String key;

    private String name;

    private String description;

    private String buildFile;

    private String buildCommand;

    private String environmentVars;

//    private String workingDirectory;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getBuildCommand() {
        return buildCommand;
    }

    public void setBuildCommand(String buildCommand) {
        this.buildCommand = buildCommand;
    }

    public String getEnvironmentVars() {
        return environmentVars;
    }

    public void setEnvironmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
    }

//    public String getWorkingDirectory() {
//        return workingDirectory;
//    }
//
//    public void setWorkingDirectory(String workingDirectory) {
//        this.workingDirectory = workingDirectory;
//    }
}
