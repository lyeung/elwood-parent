package org.lyeung.elwood.common.command.event.impl;

import org.lyeung.elwood.common.SystemException;
import org.lyeung.elwood.common.event.AbstractEventData;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorEventData extends AbstractEventData<byte[]> {

    public ShellCommandExecutorEventData(byte[] data) {
        super(data);
    }

    @Override
    protected byte[] clone(byte[] data) {
        byte[] buffer = new byte[data.length];
        System.arraycopy(data, 0, buffer, 0, data.length);
        return buffer;
    }
}
