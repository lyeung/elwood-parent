package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.SystemException;
import org.lyeung.elwood.common.command.ShellCommandExecutorEvent;
import org.lyeung.elwood.common.command.ShellCommandExecutorListener;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandExecutorListenerImpl implements ShellCommandExecutorListener {

    //private PrintWriter writer;

    //public ShellCommandExecutorListenerImpl(PrintWriter writer) {
    //    this.writer = writer;
    //}

    private Consumer<ShellCommandExecutorEvent> consumer;

    public ShellCommandExecutorListenerImpl(Consumer<ShellCommandExecutorEvent> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void handleEvent(ShellCommandExecutorEvent event) {
        consumer.accept(event);
    }
}
