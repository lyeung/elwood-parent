package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.command.BuildJobCommandFactory;

import java.util.concurrent.Callable;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildTask implements Callable<Integer> {

    private final BuildJobCommandFactory buildJobCommandFactory;

    private final String key;

    public BuildTask(BuildJobCommandFactory buildJobCommandFactory, String key) {
        this.buildJobCommandFactory = buildJobCommandFactory;
        this.key = key;
    }

    @Override
    public Integer call() throws Exception {
        return buildJobCommandFactory
                .makeCommand()
                .execute(key);
    }
}
