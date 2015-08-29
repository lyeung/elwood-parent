package org.lyeung.elwood.executor.command;

/**
 * Created by lyeung on 30/08/2015.
 */
public class KeyCountTuple {

    private final String key;

    private final long count;

    public KeyCountTuple(String key, long count) {
        this.key = key.toUpperCase();
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public long getCount() {
        return count;
    }

}
