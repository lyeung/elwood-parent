package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.repository.CountRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by lyeung on 29/08/2015.
 */
public abstract class AbstractCountRepository<K extends Serializable>
        implements CountRepository<K> {

    private final K domainKey;

    private final RedisTemplate<K, Long> template;

    public AbstractCountRepository(K domainKey, RedisTemplate<K, Long> template) {
        this.domainKey = domainKey;
        this.template = template;
    }

    public K getDomainKey() {
        return domainKey;
    }

    @Override
    public Long incrementBy(K key, long delta) {
        return template.opsForHash().increment(domainKey, key, delta);
    }

    @Override
    public Long getCount(K key) {
        if (!template.opsForHash().hasKey(domainKey, key)) {
            return null;
        }

        return incrementBy(key, 0L);
    }

    @Override
    public Set<K> findAll() {
        final HashOperations<K, K, Long> ops = template.opsForHash();
        return ops.entries(domainKey).keySet();
    }

    @Override
    public void delete(List<K> keys) {
        if (keys.isEmpty()) {
            return;
        }

        template.opsForHash().delete(domainKey, keys.toArray());
    }
}
