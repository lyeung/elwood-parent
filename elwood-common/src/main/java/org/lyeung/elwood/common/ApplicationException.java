package org.lyeung.elwood.common;

/**
 * Created by lyeung on 9/07/2015.
 */
public class ApplicationException extends Exception {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
