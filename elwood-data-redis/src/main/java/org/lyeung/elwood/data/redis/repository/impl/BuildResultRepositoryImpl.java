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

import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildResultRepositoryImpl implements BuildResultRepository {

    private final HashRepository<BuildResult, String, String> repository;

    public BuildResultRepositoryImpl(HashRepository<BuildResult, String, String> repository) {
        this.repository = repository;
    }

    @Override
    public List<BuildResult> findAll(BuildKey buildKey, long start, long end) {
        return repository.findAll(buildKey.toStringValue(), start, end);
    }

    @Override
    public void save(BuildResult value) {
        repository.save(value.getKey().getBuildKey().toStringValue(),
                value.getKey().toStringValue(), value);
    }

    @Override
    public void delete(List<BuildResultKey> objectKeys) {
        objectKeys.forEach(k ->
                repository.delete(k.getBuildKey().toStringValue(),
                Collections.singletonList(k.toStringValue())));
    }

    @Override
    public Optional<BuildResult> getOne(BuildResultKey buildResultKey) {
        return repository.getOne(buildResultKey.getBuildKey().toStringValue(),
                buildResultKey.toStringValue());
    }
}
