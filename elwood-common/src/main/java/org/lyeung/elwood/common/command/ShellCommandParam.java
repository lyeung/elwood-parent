package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandParam {

    private String command;

    private String directory;

    private boolean redirectErrorStream;

    private String environmentVars;

    ShellCommandParam() {
        // do-nothing
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isRedirectErrorStream() {
        return redirectErrorStream;
    }

    public void setRedirectErrorStream(boolean redirectErrorStream) {
        this.redirectErrorStream = redirectErrorStream;
    }

    public String getEnvironmentVars() {
        return environmentVars;
    }

    public void setEnvironmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
    }
}
