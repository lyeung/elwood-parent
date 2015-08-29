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
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lyeung on 29/07/2015.
 */
public class ProjectRepositoryImpl extends AbstractRepository<Project, String>
        implements ProjectRepository {

    public ProjectRepositoryImpl(String domainKey, RedisTemplate<String, Project> template) {
        super(domainKey, template);
    }

}
