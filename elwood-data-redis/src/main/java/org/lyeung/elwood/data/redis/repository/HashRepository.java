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

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 1/08/2015.
 */
public interface HashRepository<HV, HK extends Serializable, OK extends Serializable> {

    Optional<HV> getOne(HK key, OK objectKey);

    void save(HK key, OK objectKey, HV value);

    void delete(HK key, List<OK> objectKeys);

    List<HV> findAll(HK key, long start, long end);
}
