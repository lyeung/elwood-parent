package org.lyeung.elwood.builder;

/**
 * Created by lyeung on 9/07/15.
 */
public class BuildEvent {

    public enum EventType {ERROR_STREAM, INPUT_STREAM}

    private final EventType eventType;

    private final BuildEventData eventData;

    public BuildEvent(EventType eventType, BuildEventData eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public BuildEventData getEventData() {
        return eventData;
    }
}
