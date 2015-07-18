package org.lyeung.elwood.common.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 13/07/2015.
 */
public class ShellCommandExecutorException extends SystemException {

    public ShellCommandExecutorException(String message) {
        super(message);
    }

    public ShellCommandExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
