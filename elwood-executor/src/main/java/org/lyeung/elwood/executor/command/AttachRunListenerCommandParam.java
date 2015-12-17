package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.data.redis.domain.Build;

import java.io.File;

/**
 * Created by lyeung on 12/12/2015.
 */
public class AttachRunListenerCommandParam {

    private Build build;

    private File outputDir;

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }
}
