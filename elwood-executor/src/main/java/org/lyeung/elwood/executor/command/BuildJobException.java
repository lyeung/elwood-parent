package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 18/08/2015.
 */
public class BuildJobException extends SystemException {

    private final String key;

    public BuildJobException(String message, String key) {
        super(message);
        this.key = key;
    }

    public BuildJobException(String message, Throwable cause, String key) {
        super(message, cause);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
