package org.lyeung.elwood.maven;

import org.apache.maven.model.Model;
import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 22/11/2015.
 */
public class WritePomException extends SystemException {

    private final Model model;

    public WritePomException(String message, Throwable cause, Model model) {
        super(message, cause);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
