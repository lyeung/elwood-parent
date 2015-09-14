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

import org.lyeung.elwood.data.redis.repository.HashRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 1/08/2015.
 */
public class RedisHashRepositoryImpl<HV, HK extends Serializable, OK extends Serializable>
        implements HashRepository<HV, HK, OK> {

    private final RedisTemplate<HK, HV> template;

    public RedisHashRepositoryImpl(RedisTemplate<HK, HV> template) {
        this.template = template;
    }

    @Override
    public Optional<HV> getOne(HK hashKey, OK objectKey) {
        final HashOperations<HK, OK, HV> ops = template.opsForHash();
        final HV hv = ops.get(hashKey, objectKey);
        if (hv == null) {
            return Optional.empty();
        }

        return Optional.of(hv);
    }

    @Override
    public void save(HK hashKey, OK objectKey, HV value) {
        template.opsForHash().put(hashKey, objectKey, value);
    }

    // TODO: fix to use start and end parameters
    @Override
    public List<HV> findAll(HK hashKey, long start, long end) {
        final HashOperations<HK, OK, HV> ops = template.opsForHash();
        return ops.values(hashKey);
    }

    @Override
    public void delete(HK hashKey, List<OK> objectKeys) {
        if (objectKeys.isEmpty()) {
            return;
        }

        final HashOperations<HK, OK, HV> template = this.template.opsForHash();
        template.delete(hashKey, objectKeys.toArray());
    }
}
