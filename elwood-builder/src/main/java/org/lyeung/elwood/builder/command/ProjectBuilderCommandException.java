package org.lyeung.elwood.builder.command;

import org.lyeung.elwood.builder.BuilderException;
import org.lyeung.elwood.builder.model.ProjectModel;

/**
 * Created by lyeung on 11/07/2015.
 */
public class ProjectBuilderCommandException extends BuilderException {

    public ProjectBuilderCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectBuilderCommandException(String message) {
        super(message);
    }
}
