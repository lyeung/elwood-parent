package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.AbstractDomain;
import org.lyeung.elwood.data.redis.repository.CrudRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyeung on 1/08/2015.
 */
public abstract class AbstractRepository<V extends AbstractDomain, K extends Serializable>
        implements CrudRepository<V, K> {

    private final K domainKey;

    private final RedisTemplate<K, V> template;

    public AbstractRepository(K domainKey, RedisTemplate<K, V> template) {
        this.domainKey = domainKey;
        this.template = template;
    }

    public K getDomainKey() {
        return domainKey;
    }

    public RedisTemplate<K, V> getTemplate() {
        return template;
    }

    @Override
    public V getOne(K key) {
        return (V) getTemplate().opsForHash().get(getDomainKey(), key);
    }

    @Override
    public void save(V value) {
        getTemplate().opsForHash().put(getDomainKey(), value.getKey(), value);
    }

    @Override
    public List<V> findAll() {
        final HashOperations<K, K, V> ops = getTemplate().opsForHash();
        return ops.values(getDomainKey());
    }

    @Override
    public void delete(List<K> keys) {
        if (keys.isEmpty()) {
            return;
        }
        getTemplate().opsForHash().delete(getDomainKey(), keys.toArray());
    }
}
