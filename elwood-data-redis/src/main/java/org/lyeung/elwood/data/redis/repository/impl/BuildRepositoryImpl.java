package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by lyeung on 1/08/2015.
 */
public class BuildRepositoryImpl extends AbstractRepository<Build, String>
        implements BuildRepository {

    public BuildRepositoryImpl(
            String domainKey, RedisTemplate<String, Build> template) {

        super(domainKey, template);
    }

}
