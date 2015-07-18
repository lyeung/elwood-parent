package org.lyeung.elwood.builder;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 9/07/2015.
 */
public class BuilderException extends SystemException {

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(String message) {
        super(message);
    }
}
