package org.lyeung.elwood.data.redis.domain;

/**
 * Created by lyeung on 1/08/2015.
 */
public class Build extends AbstractDomain {

    private String buildCommand;

    private String environmentVars;

    private String workingDirectory;

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

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
