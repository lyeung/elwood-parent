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

    @Override
    public Build getOne(String key) {
        return (Build) getTemplate().opsForHash().get(getDomainKey(), key);
    }

    @Override
    public List<Build> findAll() {
        HashOperations<String,String,Build> ops = getTemplate().opsForHash();
        return ops.values(getDomainKey());
    }

    @Override
    public void save(Build domain) {
        getTemplate().opsForHash().put(getDomainKey(), domain.getKey(), domain);
    }

    @Override
    public void delete(List<String> keys) {
        if (keys.isEmpty()) {
            return;
        }

        getTemplate().opsForHash().delete(getDomainKey(), keys.toArray(new String[keys.size()]));
    }
}
