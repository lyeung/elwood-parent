package org.lyeung.elwood.builder;

/**
 * Created by lyeung on 11/07/2015.
 */
public class BuildEventData {

    private byte[] data;

    public BuildEventData(byte[] data) {
        this.data = data.clone();
    }

    public byte[] getData() {
        return data.clone();
    }
}
