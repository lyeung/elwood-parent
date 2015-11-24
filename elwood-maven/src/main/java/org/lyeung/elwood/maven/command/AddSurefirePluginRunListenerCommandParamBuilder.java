package org.lyeung.elwood.maven.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyeung on 23/11/2015.
 */
public class AddSurefirePluginRunListenerCommandParamBuilder {

    private String pomFile;

    private List<String> runListenerClassNames = new ArrayList<>();

    public AddSurefirePluginRunListenerCommandParamBuilder pomFile(String pomFile) {
        this.pomFile = pomFile;
        return this;
    }

    public AddSurefirePluginRunListenerCommandParamBuilder runListenerClassNames(
            List<String> runListenerClassNames) {

        this.runListenerClassNames = runListenerClassNames;
        return this;
    }

    public AddSurefirePluginRunListenerCommandParam build() {
        final AddSurefirePluginRunListenerCommandParam param =
                new AddSurefirePluginRunListenerCommandParam();
        param.setPomFile(pomFile);
        param.setRunListenerClassNames(runListenerClassNames);

        return param;
    }
}
