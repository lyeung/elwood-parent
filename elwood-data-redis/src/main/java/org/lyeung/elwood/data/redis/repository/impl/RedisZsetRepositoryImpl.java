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

import org.lyeung.elwood.data.redis.repository.Scorable;
import org.lyeung.elwood.data.redis.repository.ZsetRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by lyeung on 8/09/2015.
 */
public class RedisZsetRepositoryImpl<HV, HK extends Serializable>
        implements ZsetRepository<HV, HK> {

    private final RedisTemplate<HK, HV> template;

    private final Scorable<HV> scorable;

    public RedisZsetRepositoryImpl(RedisTemplate<HK, HV> template, Scorable<HV> scorable) {
        this.template = template;
        this.scorable = scorable;
    }

    @Override
    public Set<HV> findAll(HK hashKey, long start, long end) {
        return template.opsForZSet().range(hashKey, start, end);
    }

    @Override
    public Long save(HK hashKey, HV value) {
        return template.opsForZSet().add(hashKey, value, score(value)) ? 1L : 0L;
    }

    @Override
    public Long delete(HK hashKey, HV value) {
        return template.opsForZSet().remove(hashKey, value);
    }

    @Override
    public double score(HV value) {
        return scorable.score(value);
    }
}
