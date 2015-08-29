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

package org.lyeung.elwood.common.command.impl;


import org.lyeung.elwood.common.command.ShellCommandExecutor;
import org.lyeung.elwood.common.command.ShellCommandExecutorException;
import org.lyeung.elwood.common.command.event.impl.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.event.Event;
import org.lyeung.elwood.common.event.EventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorImpl implements ShellCommandExecutor {

    private static final int BUF_SIZE = 1024;

    private List<EventListener<ShellCommandExecutorEventData>> executorListeners;

    public ShellCommandExecutorImpl(List<EventListener<ShellCommandExecutorEventData>> executorListeners) {
        this.executorListeners = executorListeners;
    }

    @Override
    public Integer execute(Process process) {
        try (BufferedInputStream instream = new BufferedInputStream(process.getInputStream())) {
            byte[] buf = new byte[BUF_SIZE];
            while (true) {
                final int bytesRead = instream.read(buf);
                if (bytesRead == -1) {
                    break;
                }

                executorListeners.forEach(
                        e -> e.handleEvent(
                                new Event<>(new ShellCommandExecutorEventData(copyBuf(buf, bytesRead)))));
            }
            return process.waitFor();
        } catch (IOException e) {
            throw new ShellCommandExecutorException("unable to execute shell command", e);
        } catch (InterruptedException e) {
            throw new ShellCommandExecutorException("interrupted executing shell command", e);
        }
    }

    private byte[] copyBuf(byte[] buf, int bytesRead) {
        byte[] newBuf = new byte[bytesRead];
        System.arraycopy(buf, 0, newBuf, 0, bytesRead);
        return newBuf;
    }
}
