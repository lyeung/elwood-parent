package org.lyeung.elwood.vcs.command.impl;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.lyeung.elwood.common.conveter.impl.ByteArrayConverterImpl;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandException;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.Event;
import org.lyeung.elwood.vcs.command.EventListener;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by lyeung on 16/07/2015.
 */
public class GitCloneCommandImpl implements CloneCommand {

    private final List<EventListener<Event<GitCloneEventData>>> listeners;

    public GitCloneCommandImpl(List<EventListener<Event<GitCloneEventData>>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public File execute(CloneCommandParam cloneCommandParam) {
        final File localDirectory = new File(cloneCommandParam.getLocalDirectory());
        validateLocalDirectory(localDirectory, cloneCommandParam);

        try {
            Git.cloneRepository().setProgressMonitor(new TextProgressMonitor(new ProgressMonitorWriter(listeners)))
                    .setURI(cloneCommandParam.getRemoteUri())
                    .setDirectory(localDirectory)
                    .call();
        } catch (GitAPIException e) {
            throw new CloneCommandException("unable to clone from remote uri", e, cloneCommandParam);
        }

        return localDirectory;
    }

    private void validateLocalDirectory(File localDirectory, CloneCommandParam cloneCommandParam) {
        if (!localDirectory.exists()) {
            throw new CloneCommandException("local directory does not exist", cloneCommandParam);
        }

        if (!localDirectory.isDirectory()) {
            throw new CloneCommandException("local directory is not a directory attribute", cloneCommandParam);
        }
    }

    private static final class ProgressMonitorWriter extends Writer {

        private final List<EventListener<Event<GitCloneEventData>>> listeners;

        public ProgressMonitorWriter(List<EventListener<Event<GitCloneEventData>>> listeners) {
            this.listeners = listeners;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            final char[] buffer = new char[len - off];
            System.arraycopy(cbuf, off, buffer, 0, len - off);
            listeners.forEach(e -> e.handleEvent(new Event<>(new GitCloneEventData(
                    new ByteArrayConverterImpl().convert(buffer)))));
        }

        @Override
        public void flush() throws IOException {
            // do-nothing
        }

        @Override
        public void close() throws IOException {
            // do-nothing
        }
    }
}
