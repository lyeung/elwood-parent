package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandParamBuilder {

    private String command;

    private String directory;

    private boolean redirectErrorStream;

    private String environmentVars;

    public String getCommand() {
        return command;
    }

    public ShellCommandParamBuilder command(String command) {
        this.command = command;
        return this;
    }

    public ShellCommandParamBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }

    public ShellCommandParamBuilder redirectErrorStream(boolean redirectErrorStream) {
        this.redirectErrorStream = redirectErrorStream;
        return this;
    }

    public ShellCommandParamBuilder environmentVars(String environmentVars) {
        this.environmentVars = environmentVars;
        return this;
    }

    public ShellCommandParam build() {
        ShellCommandParam param = new ShellCommandParam();
        param.setCommand(command);
        param.setDirectory(directory);
        param.setEnvironmentVars(environmentVars);
        param.setRedirectErrorStream(redirectErrorStream);

        return param;
    }
}
