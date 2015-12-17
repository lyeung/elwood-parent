package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.data.redis.domain.Build;

import java.io.File;

/**
 * Created by lyeung on 15/12/2015.
 */
public class AttachRunListenerCommandParamBuilder {

    private Build build;

    private File outputDir;

    public AttachRunListenerCommandParamBuilder build(Build build) {
        this.build = build;
        return this;
    }

    public AttachRunListenerCommandParamBuilder outputDir(File outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public AttachRunListenerCommandParam build() {
        final AttachRunListenerCommandParam param = new AttachRunListenerCommandParam();
        param.setBuild(build);
        param.setOutputDir(outputDir);

        return param;
    }
}
