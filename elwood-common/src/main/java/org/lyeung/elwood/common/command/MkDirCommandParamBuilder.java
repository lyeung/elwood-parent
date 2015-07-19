package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 19/07/2015.
 */
public class MkDirCommandParamBuilder {

    private String directory;

    public MkDirCommandParamBuilder directory(String directory) {
        this.directory = directory;
        return this;
    }

    public MkDirCommandParam build() {
        final MkDirCommandParam param = new MkDirCommandParam();
        param.setDirectory(directory);

        return param;
    }
}
