package org.lyeung.elwood.web.controller.runbuild;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by lyeung on 13/08/2015.
 */
public class KeyTuple {
    private final String key;

    @JsonCreator
    public KeyTuple(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
