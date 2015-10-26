/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.web.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.lyeung.elwood.vcs.command.CloneCommandParam;

import javax.validation.constraints.NotNull;

/**
 * Created by lyeung on 3/08/2015.
 */
public class BuildJob {

    @NotEmpty(message = "Required")
    private String key;

    @NotEmpty(message = "Required")
    private String name;

    private String description;

    private String buildFile;

    @NotEmpty(message = "Required")
    private String buildCommand;

    private String environmentVars;

    @NotEmpty(message = "Required")
    private String sourceUrl;

    @NotNull(message = "Required")
    private CloneCommandParam.AuthenticationType authenticationType;

    @NotEmpty(message = "Required")
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
