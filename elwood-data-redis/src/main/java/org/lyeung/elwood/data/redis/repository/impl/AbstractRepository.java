/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
