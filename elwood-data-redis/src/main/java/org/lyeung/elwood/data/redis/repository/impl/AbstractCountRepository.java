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
