package org.lyeung.elwood.vcs.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 16/07/2015.
 */
public class CloneCommandException extends SystemException {

    private final CloneCommandParam cloneCommandParam;

    public CloneCommandException(String message,
        CloneCommandParam cloneCommandParam) {

        super(message);
        this.cloneCommandParam = cloneCommandParam;
    }

    public CloneCommandException(String message, Throwable cause,
        CloneCommandParam cloneCommandParam) {

        super(message, cause);
        this.cloneCommandParam = cloneCommandParam;
    }

    public CloneCommandParam getCloneCommandParam() {
        return cloneCommandParam;
    }
}
