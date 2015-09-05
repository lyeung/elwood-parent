/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.vcs.command.impl;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FS;
import org.lyeung.elwood.common.conveter.impl.ByteArrayConverterImpl;
import org.lyeung.elwood.common.event.Event;
import org.lyeung.elwood.common.event.EventListener;
import org.lyeung.elwood.vcs.command.CloneCommand;
import org.lyeung.elwood.vcs.command.CloneCommandException;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.vcs.command.CloneCommandParam.AuthenticationType;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by lyeung on 16/07/2015.
 */
public class GitCloneCommandImpl implements CloneCommand {

    private final List<EventListener<GitCloneEventData>> listeners;

    public GitCloneCommandImpl(List<EventListener<GitCloneEventData>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public File execute(CloneCommandParam cloneCommandParam) {
        final File localDirectory =
                new File(cloneCommandParam.getLocalDirectory());
        validateLocalDirectory(localDirectory, cloneCommandParam);

        try {
            final org.eclipse.jgit.api.CloneCommand cloneCommand =
                    Git.cloneRepository()
                            .setProgressMonitor(new TextProgressMonitor(
                                    new ProgressMonitorWriter(listeners)))
                            .setURI(cloneCommandParam.getRemoteUri())
                            .setDirectory(localDirectory);

            if (cloneCommandParam.isUsernamePasswordAuthentication()) {
                cloneCommand.setCredentialsProvider(
                        new UsernamePasswordCredentialsProvider(
                                cloneCommandParam.getUsername(),
                                cloneCommandParam.getPassword()));
            } else if (cloneCommandParam.isUsePublicKeyAuthentication()) {
                cloneCommand.setTransportConfigCallback(
                        new CustomTransportConfigCallback(cloneCommandParam));
            } else {
                // do-nothing
            }

            cloneCommand.call();
        } catch (GitAPIException e) {
            throw new CloneCommandException("unable to clone from remote uri",
                    e, cloneCommandParam);
        }

        return localDirectory;
    }

    private void validateLocalDirectory(
            File localDirectory, CloneCommandParam cloneCommandParam) {

        if (!localDirectory.exists()) {
            throw new CloneCommandException(
                    "local directory does not exist", cloneCommandParam);
        }

        if (!localDirectory.isDirectory()) {
            throw new CloneCommandException(
                    "local directory is not a directory attribute",
                    cloneCommandParam);
        }
    }

    private static final class ProgressMonitorWriter extends Writer {

        private final List<EventListener<GitCloneEventData>> listeners;

        public ProgressMonitorWriter(
                List<EventListener<GitCloneEventData>> listeners) {

            this.listeners = listeners;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            final char[] buffer = new char[len - off];
            System.arraycopy(cbuf, off, buffer, 0, len - off);
            listeners.forEach(e -> e.handleEvent(
                    new Event<>(new GitCloneEventData(
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

    private static class CustomTransportConfigCallback
            implements TransportConfigCallback {

        private final CloneCommandParam param;

        public CustomTransportConfigCallback(CloneCommandParam param) {
            this.param = param;
        }

        @Override
        public void configure(Transport transport) {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(
                    new CustomIdentityFileJschConfigSessionFactory(param));
        }
    }

    private static class CustomIdentityFileJschConfigSessionFactory
            extends JschConfigSessionFactory {

        private final CloneCommandParam param;

        public CustomIdentityFileJschConfigSessionFactory(
                CloneCommandParam param) {

            this.param = param;
        }

        @Override
        protected JSch getJSch(OpenSshConfig.Host hc, FS fs)
                throws JSchException {

            final JSch jSch = super.getJSch(hc, fs);
            jSch.addIdentity(param.getIdentityKey());

            return jSch;
        }

        @Override
        protected void configure(OpenSshConfig.Host hc, Session session) {
            session.setUserInfo(new CustomUserInfo(param));
        }
    }

    private static class CustomUserInfo implements UserInfo {

        private final CloneCommandParam param;

        public CustomUserInfo(CloneCommandParam param) {
            this.param = param;
        }

        @Override
        public String getPassphrase() {
            return param.getPassphrase();
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String message) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return param.getAuthenticationType()
                    == AuthenticationType.PUBLIC_KEY_PASSPHRASE;
        }

        @Override
        public boolean promptYesNo(String message) {
            return false;
        }

        @Override
        public void showMessage(String message) {
            // do-nothing
        }
    }
}
