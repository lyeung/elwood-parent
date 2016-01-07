package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.command.GetBuildResultCommand;

import java.util.Optional;

/**
 * Created by lyeung on 25/12/2015.
 */
public class GetBuildResultCommandImpl implements GetBuildResultCommand {

    private final BuildResultRepository repository;

    public GetBuildResultCommandImpl(BuildResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BuildResult> execute(BuildResultKey buildResultKey) {
        return repository.getOne(buildResultKey);
    }
}
