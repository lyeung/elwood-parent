package org.lyeung.elwood.maven.command;

/**
 * Created by lyeung on 23/11/2015.
 */
public class AddSurefirePluginRunListenerCommandParamBuilder {

    private String pomFile;


    public AddSurefirePluginRunListenerCommandParamBuilder pomFile(String pomFile) {
        this.pomFile = pomFile;
        return this;
    }

    public AddSurefirePluginRunListenerCommandParam build() {
        final AddSurefirePluginRunListenerCommandParam param =
                new AddSurefirePluginRunListenerCommandParam();
        param.setPomFile(pomFile);

        return param;
    }
}
