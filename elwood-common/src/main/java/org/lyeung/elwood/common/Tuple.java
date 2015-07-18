package org.lyeung.elwood.common;

/**
 * Created by lyeung on 9/07/2015.
 */
public class Tuple<A, B> {

    private final A value1;

    private final B value2;

    public Tuple(A value1, B value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public A getValue1() {
        return value1;
    }

    public B getValue2() {
        return value2;
    }
}
