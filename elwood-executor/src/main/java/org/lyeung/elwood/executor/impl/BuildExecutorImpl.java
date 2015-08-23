package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildExecutorImpl implements BuildExecutor {

    private final BuildJobCommandFactory factory;

    private final ExecutorService executorService;

    public BuildExecutorImpl(BuildJobCommandFactory factory, ExecutorService executorService) {
        this.factory = factory;
        this.executorService = executorService;
    }

    @Override
    public Future<Integer> add(String key) {
        return executorService.submit(new BuildTask(factory, key));
    }
}
