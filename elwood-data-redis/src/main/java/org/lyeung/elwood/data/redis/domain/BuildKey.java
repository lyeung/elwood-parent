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

package org.lyeung.elwood.data.redis.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by lyeung on 23/09/2015.
 */
public class BuildKey implements Keyable, Serializable {

    private String key;

    private BuildKey() {
        // do-nothing
    }

    @JsonCreator
    public BuildKey(String key) {
        this.key = key;
    }


    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BuildKey buildKey = (BuildKey) obj;
        return Objects.equals(key, buildKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toStringValue() {
        return key;
    }

}
