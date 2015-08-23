package org.lyeung.elwood.web.model;

import org.lyeung.elwood.vcs.command.CloneCommandParam;

/**
 * Created by lyeung on 3/08/2015.
 */
public class BuildJob {

    private String key;

    private String name;

    private String description;

    private String buildFile;

    private String buildCommand;

    private String environmentVars;

    private String sourceUrl;

    private CloneCommandParam.AuthenticationType authenticationType;

    private String identityKey;

    private String passphrase;

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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public CloneCommandParam.AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(CloneCommandParam.AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
}
