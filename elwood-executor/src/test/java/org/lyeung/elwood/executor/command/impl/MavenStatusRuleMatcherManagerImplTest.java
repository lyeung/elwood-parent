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