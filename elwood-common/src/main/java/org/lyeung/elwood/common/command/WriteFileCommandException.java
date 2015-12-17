package org.lyeung.elwood.common.command;

import org.lyeung.elwood.common.SystemException;

import java.io.File;

/**
 * Created by lyeung on 16/12/2015.
 */
public class WriteFileCommandException extends SystemException {

    private final File file;

    public WriteFileCommandException(String message, Throwable cause, File file) {
        super(message, cause);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
