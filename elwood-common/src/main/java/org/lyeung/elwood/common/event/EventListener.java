package org.lyeung.elwood.common.event;

/**
 * Created by lyeung on 17/07/2015.
 */
public interface EventListener<T extends AbstractEventData> {

    void handleEvent(Event<T> event);
}
