package org.lyeung.elwood.common.command;

import java.io.File;

/**
 * Created by lyeung on 16/12/2015.
 */
public class WriteFileCommandParam {

    private String content;

    private File file;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
