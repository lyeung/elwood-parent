package org.lyeung.elwood.executor.impl;

import org.lyeung.elwood.executor.command.BuildJobCommandFactory;
import org.lyeung.elwood.executor.command.KeyCountTuple;

import java.util.concurrent.Callable;

/**
 * Created by lyeung on 14/08/2015.
 */
public class BuildTask implements Callable<Integer> {

    private final BuildJobCommandFactory buildJobCommandFactory;

    private final String key;

    private final long count;

    public BuildTask(BuildJobCommandFactory buildJobCommandFactory, String key, long count) {
        this.buildJobCommandFactory = buildJobCommandFactory;
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public long getCount() {
        return count;
    }

    @Override
    public Integer call() throws Exception {
        return buildJobCommandFactory
                .makeCommand()
                .execute(new KeyCountTuple(key, count));
    }
}
