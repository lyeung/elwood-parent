package org.lyeung.elwood.maven.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyeung on 23/11/2015.
 */
public class AddSurefirePluginRunListenerCommandParam {

    private String pomFile;

    private List<String> runListenerClassNames = new ArrayList<>();

    public String getPomFile() {
        return pomFile;
    }

    public void setPomFile(String pomFile) {
        this.pomFile = pomFile;
    }

    public List<String> getRunListenerClassNames() {
        return runListenerClassNames;
    }

    public void setRunListenerClassNames(List<String> runListenerClassNames) {
        this.runListenerClassNames = runListenerClassNames;
    }
}
