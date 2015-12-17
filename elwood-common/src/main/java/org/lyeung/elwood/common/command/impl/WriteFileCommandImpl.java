package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.command.WriteFileCommand;
import org.lyeung.elwood.common.command.WriteFileCommandException;
import org.lyeung.elwood.common.command.WriteFileCommandParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lyeung on 16/12/2015.
 */
public class WriteFileCommandImpl implements WriteFileCommand {

    @Override
    public File execute(WriteFileCommandParam param) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(param.getFile()))) {
            writer.write(param.getContent());
            return param.getFile();
        } catch (IOException e) {
            throw new WriteFileCommandException("unable to write to file=["
                    + param.getFile().getAbsolutePath() + "]", e, param.getFile());
        }
    }
}
