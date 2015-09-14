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
public class CountRepositoryImpl<HK extends Serializable, OK extends Serializable>
        implements CountRepository<HK, OK> {

    private final RedisTemplate<HK, Long> template;

    public CountRepositoryImpl(RedisTemplate<HK, Long> template) {
        this.template = template;
    }

    @Override
    public Long incrementBy(HK hashKey, OK objectKey, long delta) {
        return template.opsForHash().increment(hashKey, objectKey, delta);
    }

    @Override
    public Long getCount(HK hashKey, OK objectKey) {
        if (!template.opsForHash().hasKey(hashKey, objectKey)) {
            return null;
        }

        return incrementBy(hashKey, objectKey, 0L);
    }

    @Override
    public Set<OK> findAll(HK hashKey) {
        final HashOperations<HK, OK, Long> ops = template.opsForHash();
        return ops.entries(hashKey).keySet();
    }

    @Override
    public void delete(HK hashKey, List<OK> objectKeys) {
        if (objectKeys.isEmpty()) {
            return;
        }

        template.opsForHash().delete(hashKey, objectKeys.toArray());
    }
}
