package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public abstract class AbstractEventData<T> {

    private T data;

    public AbstractEventData(T data) {
        this.data = clone(data);
    }

    protected abstract T clone(T data);

    public T getData() {
        return clone(data);
    }
}
