package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.command.SaveBuildResultCommand;

/**
 * Created by lyeung on 25/12/2015.
 */
public class SaveBuildResultCommandImpl implements SaveBuildResultCommand {

    private final BuildResultRepository repository;

    public SaveBuildResultCommandImpl(BuildResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public BuildResult execute(BuildResult buildResult) {
        repository.save(buildResult);
        return buildResult;
    }
}
