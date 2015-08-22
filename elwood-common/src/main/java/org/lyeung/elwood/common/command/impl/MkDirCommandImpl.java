package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.command.MkDirCommand;
import org.lyeung.elwood.common.command.MkDirCommandException;
import org.lyeung.elwood.common.command.MkDirCommandParam;

import java.io.File;

/**
 * Created by lyeung on 19/07/2015.
 */
public class MkDirCommandImpl implements MkDirCommand {

    @Override
    public File execute(MkDirCommandParam param) {
        final File file = new File(param.getDirectory());
        final boolean success = file.mkdirs();
        if (!success) {
            throw new MkDirCommandException("unable to make directory ["
                    + param.getDirectory() + "]", param);
        }

        return file;
    }
}
