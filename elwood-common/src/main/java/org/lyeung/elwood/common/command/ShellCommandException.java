package org.lyeung.elwood.common.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandException extends SystemException {

    private ShellCommandParam param;

    public ShellCommandException(String message) {
        super(message);
    }

    public ShellCommandException(String message, Throwable cause, ShellCommandParam param) {
        super(message, cause);
        this.param = param;
    }

    public ShellCommandParam getParam() {
        return param;
    }
}
