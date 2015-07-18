package org.lyeung.elwood.vcs.command.impl;

import org.lyeung.elwood.vcs.command.AbstractEventData;

/**
 * Created by lyeung on 18/07/2015.
 */
public class GitCloneEventData extends AbstractEventData<byte[]> {

    public GitCloneEventData(byte[] data) {
        super(data);
    }

    @Override
    protected byte[] clone(byte[] data) {
        final byte[] result = new byte[data.length];
        System.arraycopy(data, 0, result, 0, data.length);
        return result;
    }
}
