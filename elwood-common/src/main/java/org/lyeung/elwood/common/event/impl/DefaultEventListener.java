package org.lyeung.elwood.common.event.impl;

import org.lyeung.elwood.common.event.AbstractEventData;
import org.lyeung.elwood.common.event.Event;
import org.lyeung.elwood.common.event.EventListener;

import java.util.function.Consumer;

/**
 * Created by lyeung on 18/07/2015.
 */
public class DefaultEventListener<T extends AbstractEventData<?>> implements EventListener<T> {

    private Consumer<Event<T>> consumer;

    public DefaultEventListener(Consumer<Event<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void handleEvent(Event<T> event) {
        consumer.accept(event);
    }

}
