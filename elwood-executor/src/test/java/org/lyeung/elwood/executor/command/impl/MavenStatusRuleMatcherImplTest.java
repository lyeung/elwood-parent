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

import com.github.lyeung.common.test.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 27/12/2015.
 */
@Category(QuickTest.class)
public class MavenStatusRuleMatcherImplTest {

    private static final String LINE_SUCCESS = "success: [org.lyeung.test.Class.method1,"
            + "org.lyeung.test.Class.method2,org.lyeung.test.Class.method3]";
    private static final String LINE_FAILED = "failed: [org.lyeung.test.Class.method1]";
    private static final String LINE_IGNORED = "ignored: []";

    private MavenStatusRuleMatcherImpl impl;


    @Test
    public void testIsMatch() {
        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.SUCCESS);
        assertTrue(impl.isMatch(LINE_SUCCESS));
        assertFalse(impl.isMatch(LINE_FAILED));
        assertFalse(impl.isMatch(LINE_IGNORED));

        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.FAILED);
        assertFalse(impl.isMatch(LINE_SUCCESS));
        assertTrue(impl.isMatch(LINE_FAILED));
        assertFalse(impl.isMatch(LINE_IGNORED));

        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.IGNORED);
        assertFalse(impl.isMatch(LINE_SUCCESS));
        assertFalse(impl.isMatch(LINE_FAILED));
        assertTrue(impl.isMatch(LINE_IGNORED));
    }

    @Test
    public void testApplyRule() {
        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.SUCCESS);
        Tuple<MavenStatusType, Integer> tuple = impl.applyRule(LINE_SUCCESS);
        assertEquals(MavenStatusType.SUCCESS, tuple.getValue1());
        assertEquals(3, tuple.getValue2().intValue());

        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.FAILED);
        tuple = impl.applyRule(LINE_FAILED);
        assertEquals(MavenStatusType.FAILED, tuple.getValue1());
        assertEquals(1, tuple.getValue2().intValue());

        impl = new MavenStatusRuleMatcherImpl(MavenStatusType.IGNORED);
        tuple = impl.applyRule(LINE_IGNORED);
        assertEquals(MavenStatusType.IGNORED, tuple.getValue1());
        assertEquals(0, tuple.getValue2().intValue());
    }
}