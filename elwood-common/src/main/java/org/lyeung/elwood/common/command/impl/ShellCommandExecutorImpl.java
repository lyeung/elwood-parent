package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.command.ShellCommandExecutor;
import org.lyeung.elwood.common.command.ShellCommandExecutorEvent;
import org.lyeung.elwood.common.command.ShellCommandExecutorEventData;
import org.lyeung.elwood.common.command.ShellCommandExecutorException;
import org.lyeung.elwood.common.command.ShellCommandExecutorListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorImpl implements ShellCommandExecutor {

    private static final int BUF_SIZE = 1024;

    private List<ShellCommandExecutorListener> executorListeners;

    public ShellCommandExecutorImpl(List<ShellCommandExecutorListener> executorListeners) {
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
                                new ShellCommandExecutorEvent(ShellCommandExecutorEvent.EventType.INPUT_STREAM,
                                        new ShellCommandExecutorEventData(copyBuf(buf, bytesRead)))));
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
