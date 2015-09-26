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

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 1/08/2015.
 */
public class BuildRepositoryImpl implements BuildRepository {

    private final String hashKey;

    private final HashRepository<Build, String, String> repository;

    public BuildRepositoryImpl(String hashKey,
        HashRepository<Build, String, String> repository) {

        this.hashKey = hashKey;
        this.repository = repository;
    }

    @Override
    public Optional<Build> getOne(BuildKey buildKey) {
        return repository.getOne(hashKey, buildKey.toStringValue());
    }

    @Override
    public void save(Build build) {
        repository.save(hashKey, build.getKey().toStringValue(), build);
    }

    @Override
    public void delete(List<BuildKey> buildKeys) {
        final List<String> keys = buildKeys.stream()
                .map(k -> k.toStringValue()).collect(Collectors.toList());
        repository.delete(hashKey, keys);
    }

    @Override
    public List<Build> findAll(long start, long end) {
        return repository.findAll(hashKey, start, end);
    }
}
