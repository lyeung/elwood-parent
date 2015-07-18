package org.lyeung.elwood.vcs.command;

import org.eclipse.jgit.api.Git;
import org.lyeung.elwood.common.command.Command;

import java.io.File;

/**
 * Created by lyeung on 16/07/2015.
 */
public interface CloneCommand extends Command<CloneCommandParam, File> {
    // do-nothing
}
