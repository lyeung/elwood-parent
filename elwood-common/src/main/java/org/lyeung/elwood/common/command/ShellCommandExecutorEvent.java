package org.lyeung.elwood.common.command;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorEvent {

    public enum EventType { INPUT_STREAM, ERROR_STREAM }

    private EventType eventType;

    private ShellCommandExecutorEventData eventData;

    public ShellCommandExecutorEvent(EventType eventType, ShellCommandExecutorEventData eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public ShellCommandExecutorEventData getEventData() {
        return eventData;
    }
}
