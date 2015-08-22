package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.command.BuildArticleCommandFactory;

import java.util.concurrent.Callable;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildTask implements Callable<Integer> {

    private final BuildArticleCommandFactory buildArticleCommandFactory;

    private final String key;

    public BuildTask(BuildArticleCommandFactory buildArticleCommandFactory, String key) {
        this.buildArticleCommandFactory = buildArticleCommandFactory;
        this.key = key;
    }

    @Override
    public Integer call() throws Exception {
        return buildArticleCommandFactory
                .makeCommand()
                .execute(key);
    }
}
