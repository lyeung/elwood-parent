package org.lyeung.elwood.common.command;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorEventData {

    private byte[] data = new byte[0];

    public ShellCommandExecutorEventData(byte[] data) {
        if (data == null) {
            throw new SystemException("data cannot be null");
        }

        this.data = data.clone();
    }

    public byte[] getData() {
        return data.clone();
    }
}
