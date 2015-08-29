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

import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lyeung on 24/08/2015.
 */
public class BuildResultRepositoryImpl extends AbstractRepository<BuildResult, String>
        implements BuildResultRepository {

    public BuildResultRepositoryImpl(String domainKey,
                                     RedisTemplate<String, BuildResult> template) {

        super(domainKey, template);
    }
}
