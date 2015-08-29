package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.repository.BuildCountRepository;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildCountRepositoryImpl extends AbstractCountRepository<String>
        implements BuildCountRepository {

    public BuildCountRepositoryImpl(String domainKey, RedisTemplate<String, Long> template) {
        super(domainKey, template);
    }

}
