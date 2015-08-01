package org.lyeung.elwood.data.redis.repository.impl;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lyeung on 1/08/2015.
 */
public abstract class AbstractRepository<V, K> {

    private final String domainKey;

    private final RedisTemplate<K, V> template;

    public AbstractRepository(String domainKey, RedisTemplate<K, V> template) {
        this.domainKey = domainKey;
        this.template = template;
    }

    public String getDomainKey() {
        return domainKey;
    }

    public RedisTemplate<K, V> getTemplate() {
        return template;
    }
}
