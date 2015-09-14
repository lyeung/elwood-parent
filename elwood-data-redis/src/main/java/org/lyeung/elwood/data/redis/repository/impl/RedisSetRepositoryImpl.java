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

import org.lyeung.elwood.data.redis.repository.SetRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by lyeung on 8/09/2015.
 */
public class RedisSetRepositoryImpl<HV, HK extends Serializable>
        implements SetRepository<HV, HK> {

    private RedisTemplate<HK, HV> template;

    public RedisSetRepositoryImpl(RedisTemplate<HK, HV> template) {
        this.template = template;
    }

    @Override
    public Set<HV> findAll(HK hashKey, long start, long end) {
        // TODO: set sscan
        return template.opsForSet().members(hashKey);
    }

    @Override
    public Long save(HK hashKey, HV value) {
        return template.opsForSet().add(hashKey, value);
    }

    @Override
    public Long delete(HK hashKey, HV value) {
        return template.opsForSet().remove(hashKey, value);
    }

}
