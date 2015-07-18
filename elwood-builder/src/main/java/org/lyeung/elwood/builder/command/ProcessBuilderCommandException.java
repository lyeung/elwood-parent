package org.lyeung.elwood.builder.command;

import org.lyeung.elwood.builder.BuilderException;
import org.lyeung.elwood.builder.model.BuildModel;

/**
 * Created by lyeung on 11/07/2015.
 */
public class ProcessBuilderCommandException extends BuilderException {

    private final BuildModel buildModel;

    public ProcessBuilderCommandException(String message, BuildModel buildModel) {
        super(message);
        this.buildModel = buildModel;
    }

    public ProcessBuilderCommandException(String message, Throwable cause, BuildModel buildModel) {
        super(message, cause);
        this.buildModel = buildModel;
    }

    public BuildModel getBuildModel() {
        return buildModel;
    }
}
