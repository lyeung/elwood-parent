package org.lyeung.elwood.executor.command.impl;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.test.QuickTest;
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