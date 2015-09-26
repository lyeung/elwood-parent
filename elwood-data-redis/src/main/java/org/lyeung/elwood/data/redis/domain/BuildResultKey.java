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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by lyeung on 23/09/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildResultKey implements Keyable {

    private final BuildKey buildKey;

    private final long count;

    private final String stringValue;

    private BuildResultKey() {
        buildKey = null;
        count = -1;
        stringValue = null;
    }

    @JsonCreator
    public BuildResultKey(@JsonProperty("buildKey") BuildKey buildKey,
                          @JsonProperty("count") long count) {
        this.buildKey = buildKey;
        this.count = count;
        this.stringValue = buildKey.toStringValue() + "-" + count;
    }

    public Keyable getBuildKey() {
        return buildKey;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String getKey() {
        return buildKey.getKey();
    }

    @Override
    public String toStringValue() {
        return stringValue;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BuildResultKey that = (BuildResultKey) obj;
        return Objects.equals(count, that.count)
                && Objects.equals(buildKey, that.buildKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildKey, count);
    }
}
