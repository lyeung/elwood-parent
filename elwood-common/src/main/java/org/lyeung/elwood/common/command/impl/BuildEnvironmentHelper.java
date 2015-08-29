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

package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.command.CommonConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 9/08/2015.
 */
public class BuildEnvironmentHelper {

    public Map<String, String> buildEnvironment(String environmentVars) {
        final Map<String, String> environment = new HashMap<>();

        if (environmentVars == null || environmentVars.equals("")) {
            return environment;
        }

        final String[] envVars = environmentVars.split(CommonConstants.SPACE_DELIMETER);
        List<Tuple<String, String>> tuples = Arrays.asList(envVars).stream()
                .map(e -> tuplise(e.split("=")))
                .collect(Collectors.toList());

        tuples.forEach(t -> environment.put(t.getValue1(), t.getValue2()));

        return environment;
    }


    private Tuple<String, String> tuplise(String[] args) {
        assert args.length == 2;
        return new Tuple<>(args[0], args[1]);
    }
}
