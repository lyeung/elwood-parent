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

import org.lyeung.elwood.data.redis.repository.BuildCountRepository;
import org.lyeung.elwood.data.redis.repository.CountRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildCountRepositoryImpl implements BuildCountRepository {

    private final String hashKey;

    private final CountRepository<String, String> repository;

    public BuildCountRepositoryImpl(String hashKey, CountRepository<String, String> repository) {
        this.hashKey = hashKey;
        this.repository = repository;
    }

    @Override
    public Long incrementBy(String objectKey, long delta) {
        return repository.incrementBy(hashKey, objectKey, delta);
    }

    @Override
    public Long getCount(String objectKey) {
        return repository.getCount(hashKey, objectKey);
    }

    @Override
    public Set<String> findAll() {
        return repository.findAll(hashKey);
    }

    @Override
    public void delete(List<String> objectKeys) {
        repository.delete(hashKey, objectKeys);
    }
}
