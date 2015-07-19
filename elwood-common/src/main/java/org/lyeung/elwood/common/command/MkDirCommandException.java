package org.lyeung.elwood.common.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 19/07/2015.
 */
public class MkDirCommandException extends SystemException {

    private final MkDirCommandParam param;

    public MkDirCommandException(String message, MkDirCommandParam param) {
        super(message);
        this.param = param;
    }

    public MkDirCommandParam getParam() {
        return param;
    }
}
