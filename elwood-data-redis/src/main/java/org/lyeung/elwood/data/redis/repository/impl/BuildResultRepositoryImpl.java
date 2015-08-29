package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildResultRepositoryImpl extends AbstractRepository<BuildResult, String>
        implements BuildResultRepository {

    public BuildResultRepositoryImpl(String domainKey,
                                     RedisTemplate<String, BuildResult> template) {

        super(domainKey, template);
    }
}
