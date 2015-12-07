package org.lyeung.elwood.maven;

import org.lyeung.elwood.common.SystemException;

import java.io.File;

/**
 * Created by lyeung on 20/11/2015.
 */
public class ReadPomException extends SystemException {

    private final File pomFile;

    public ReadPomException(String message, Throwable cause, File pomFile) {
        super(message, cause);
        this.pomFile = pomFile;
    }

    public File getPomFile() {
        return pomFile;
    }
}
