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

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 2/01/2016.
 */
@Category(QuickTest.class)
public class MavenStatusRuleMatcherManagerImplTest {

    private MavenStatusRuleMatcherManagerImpl manager;

    private String content;

    @Before
    public void setUp() throws IOException {
        manager = new MavenStatusRuleMatcherManagerImpl();

        content = FileUtils.readFileToString(new File(getClass().getClassLoader()
                .getResource("maven-status-rule-matcher.elwood.result").getPath()));
    }

    @Test
    public void testProcessLines() {
        Map<MavenStatusType, Tuple<MavenStatusType, Integer>> map =
                manager.processLines(Arrays.asList(content.split("\n")));

        assertEquals(3, map.size());
        assertEquals(3, map.get(MavenStatusType.SUCCESS).getValue2().intValue());
        assertEquals(2, map.get(MavenStatusType.FAILED).getValue2().intValue());
        assertEquals(1, map.get(MavenStatusType.IGNORED).getValue2().intValue());
    }

}