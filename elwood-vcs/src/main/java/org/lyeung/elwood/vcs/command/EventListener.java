package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public interface EventListener<T extends Event> {

    void handleEvent(T event);
}
