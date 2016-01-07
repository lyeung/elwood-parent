package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.common.SystemException;

import java.io.File;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandException extends SystemException {

    private final File file;

    public GetMavenStatusCommandException(String message, Throwable cause, File file) {
        super(message, cause);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
