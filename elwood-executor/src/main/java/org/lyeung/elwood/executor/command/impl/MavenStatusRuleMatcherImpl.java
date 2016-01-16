/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

/**
 * Created by lyeung on 27/12/2015.
 */
public class MavenStatusRuleMatcherImpl extends AbstractMavenStatusRuleMatcher {

    public MavenStatusRuleMatcherImpl(MavenStatusType type) {
        super(type);
    }

    @Override
    public Tuple<MavenStatusType, Integer> applyRule(String value) {
        final String[] tokens = value.split(":");
        return new Tuple<>(getType(), count(tokens[1].trim()));
    }

    private int count(String line) {
        final String content = line.substring(1, line.length() - 1);
        if ("".equals(content)) {
            return 0;
        }

        return content.split(",\\s?").length;
    }
}
