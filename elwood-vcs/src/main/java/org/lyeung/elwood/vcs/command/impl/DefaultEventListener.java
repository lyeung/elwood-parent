package org.lyeung.elwood.vcs.command.impl;

import org.lyeung.elwood.vcs.command.AbstractEventData;
import org.lyeung.elwood.vcs.command.Event;
import org.lyeung.elwood.vcs.command.EventListener;

import java.util.function.Consumer;

/**
 * Created by lyeung on 18/07/2015.
 */
public class DefaultEventListener<T extends AbstractEventData<?>> implements EventListener<Event<T>> {

    private Consumer<Event<T>> consumer;

    public DefaultEventListener(Consumer<Event<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void handleEvent(Event<T> event) {
        consumer.accept(event);
    }

}
