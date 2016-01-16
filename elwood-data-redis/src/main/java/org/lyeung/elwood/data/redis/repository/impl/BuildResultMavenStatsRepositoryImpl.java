/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 25/12/2015.
 */
public class BuildResultMavenStatsRepositoryImpl implements BuildResultMavenStatsRepository {

    private final HashRepository<BuildResultMavenStats, String, String> repository;

    private final String keyType;

    public BuildResultMavenStatsRepositoryImpl(
            String keyType, HashRepository<BuildResultMavenStats, String, String> repository) {
        this.keyType = keyType;
        this.repository = repository;
    }

    @Override
    public void save(BuildResultMavenStats value) {
        repository.save(keyType + value.getKey().toStringValue(),
                value.getKey().toStringValue(), value);
    }

    @Override
    public void delete(List<BuildResultKey> objectKeys) {
        objectKeys.forEach(k ->
                repository.delete(keyType + k.toStringValue(),
                        Collections.singletonList(k.toStringValue())));
    }

    @Override
    public Optional<BuildResultMavenStats> getOne(BuildResultKey buildResultKey) {
        return repository.getOne(keyType + buildResultKey.toStringValue(),
                buildResultKey.toStringValue());
    }
}
