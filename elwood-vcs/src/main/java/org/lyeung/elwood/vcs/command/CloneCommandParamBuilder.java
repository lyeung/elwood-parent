package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public class CloneCommandParamBuilder {

    private String remoteUri;

    private String localDirectory;

    public CloneCommandParamBuilder remoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
        return this;
    }

    public CloneCommandParamBuilder localDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
        return this;
    }

    public CloneCommandParam build() {
        CloneCommandParam param = new CloneCommandParam();
        param.setLocalDirectory(localDirectory);
        param.setRemoteUri(remoteUri);

        return param;
    }
}
