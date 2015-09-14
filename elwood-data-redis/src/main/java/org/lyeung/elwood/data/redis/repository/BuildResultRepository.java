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

package org.lyeung.elwood.data.redis.repository;

import org.lyeung.elwood.data.redis.domain.BuildResult;

import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 23/08/2015.
 */
public interface BuildResultRepository {

    List<BuildResult> findAll(String hashKey, long start, long end);

    void save(String hashKey, BuildResult value);

    void delete(String hashKey, List<String> objectKeys);

//    void delete(String hashKey, BuildResult values);

//    double score(BuildResult value);

    Optional<BuildResult> getOne(String hashKey, String objectKey);
}
