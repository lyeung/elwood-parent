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

package org.lyeung.elwood.common;

/**
 * Created by lyeung on 9/07/2015.
 */
public class Tuple<A, B> {

    private final A value1;

    private final B value2;

    public Tuple(A value1, B value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public A getValue1() {
        return value1;
    }

    public B getValue2() {
        return value2;
    }
}
