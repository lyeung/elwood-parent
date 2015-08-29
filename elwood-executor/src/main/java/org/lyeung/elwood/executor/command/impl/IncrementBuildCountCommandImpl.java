package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildCountRepository;
import org.lyeung.elwood.executor.command.IncrementBuildCountCommand;

/**
 * Created by lyeung on 29/08/2015.
 */
public class IncrementBuildCountCommandImpl implements IncrementBuildCountCommand {

    private final BuildCountRepository buildCountRepository;

    public IncrementBuildCountCommandImpl(BuildCountRepository buildCountRepository) {
        this.buildCountRepository = buildCountRepository;
    }

    @Override
    public Long execute(String key) {
        return buildCountRepository.incrementBy(key, 1L);
    }
}
