package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public class Event<T extends AbstractEventData> {

    private final T eventData;

    public Event(T eventData) {
        this.eventData = eventData;
    }

    public T getEventData() {
        return eventData;
    }
}
