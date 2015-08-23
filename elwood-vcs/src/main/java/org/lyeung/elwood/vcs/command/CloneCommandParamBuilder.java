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
