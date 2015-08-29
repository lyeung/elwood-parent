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

package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public class CloneCommandParamBuilder {

    private String remoteUri;

    private String localDirectory;

    private CloneCommandParam.AuthenticationType authenticationType;

    private String username;

    private String password;

    private String identityKey;

    private String passphrase;

    public CloneCommandParamBuilder remoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
        return this;
    }

    public CloneCommandParamBuilder localDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
        return this;
    }

    public CloneCommandParamBuilder authenticationType(
            CloneCommandParam.AuthenticationType authenticationType) {

        this.authenticationType = authenticationType;
        return this;
    }

    public CloneCommandParamBuilder username(String username) {
        this.username = username;
        return this;
    }

    public CloneCommandParamBuilder password(String password) {
        this.password = password;
        return this;
    }

    public CloneCommandParamBuilder identityKey(String identityKey) {
        this.identityKey = identityKey;
        return this;
    }

    public CloneCommandParamBuilder passphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    public CloneCommandParam build() {
        CloneCommandParam param = new CloneCommandParam();
        param.setLocalDirectory(localDirectory);
        param.setRemoteUri(remoteUri);
        param.setAuthenticationType(authenticationType);
        param.setUsername(username);
        param.setPassword(password);
        param.setIdentityKey(identityKey);
        param.setPassphrase(passphrase);

        return param;
    }
}
