package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.command.BuildArticleCommandFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildExecutorImpl implements BuildExecutor {

    private final BuildArticleCommandFactory factory;

    private final ExecutorService executorService;

    public BuildExecutorImpl(BuildArticleCommandFactory factory, ExecutorService executorService) {
        this.factory = factory;
        this.executorService = executorService;
    }

    @Override
    public Future<Integer> add(String key) {
        return executorService.submit(new BuildTask(factory, key));
    }
}
