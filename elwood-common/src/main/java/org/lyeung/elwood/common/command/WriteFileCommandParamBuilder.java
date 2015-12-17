package org.lyeung.elwood.common.command;

import java.io.File;

/**
 * Created by lyeung on 16/12/2015.
 */
public class WriteFileCommandParamBuilder {

    private String content;

    private File file;

    public WriteFileCommandParamBuilder content(String content) {
        this.content = content;
        return this;
    }

    public WriteFileCommandParamBuilder file(File file) {
        this.file = file;
        return this;
    }

    public WriteFileCommandParam build() {
        final WriteFileCommandParam param = new WriteFileCommandParam();
        param.setContent(content);
        param.setFile(file);

        return param;
    }
}
