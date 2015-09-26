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

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.domain.ProjectKey;
import org.lyeung.elwood.data.redis.repository.HashRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 29/07/2015.
 */
public class ProjectRepositoryImpl implements ProjectRepository {

    private final String hashKey;

    private final HashRepository<Project, String, String> repository;

    public ProjectRepositoryImpl(String hashKey,
        HashRepository<Project, String, String> repository) {

        this.hashKey = hashKey;
        this.repository = repository;
    }

    @Override
    public Optional<Project> getOne(ProjectKey projectKey) {
        return repository.getOne(hashKey, projectKey.toStringValue());
    }

    @Override
    public void save(Project project) {
        repository.save(hashKey, project.getKey().toStringValue(), project);
    }

    @Override
    public void delete(List<ProjectKey> projectKeys) {
        final List<String> keys = projectKeys.stream()
                .map(k -> k.toStringValue()).collect(Collectors.toList());
        repository.delete(hashKey, keys);
    }

    @Override
    public List<Project> findAll(long start, long end) {
        return repository.findAll(hashKey, start, end);
    }
}
