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

package org.lyeung.elwood.executor.command;

import java.io.Serializable;

/**
 * Created by lyeung on 30/08/2015.
 */
public class KeyCountTuple implements Serializable {

    private final String key;

    private final long count;

    private final String stringRepresentation;

    public KeyCountTuple(String key, long count) {
        this.key = key.toUpperCase();
        this.count = count;
        stringRepresentation = key + "-" + count;
    }

    public String getKey() {
        return key;
    }

    public long getCount() {
        return count;
    }

    private String getStringRepresentation() {
        return stringRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        KeyCountTuple tuple = (KeyCountTuple) obj;

        return stringRepresentation.equals(tuple.stringRepresentation);

    }

    @Override
    public int hashCode() {
        return stringRepresentation.hashCode();
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}
