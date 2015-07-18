package org.lyeung.elwood.common;

/**
 * Created by lyeung on 9/07/2015.
 */
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
