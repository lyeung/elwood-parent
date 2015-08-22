package org.lyeung.elwood.executor;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lyeung.elwood.common.test.QuickTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lyeung on 15/08/2015.
 */
@Category(QuickTest.class)
public class BuildMapLogTest {

    private static final String KEY = "KEY";
    private BuildMapLog buildMapLog;

    @Test
    public void testAppend() throws Exception {
        buildMapLog = new BuildMapLog(2);
        assertFalse(buildMapLog.get(KEY).isPresent());

        buildMapLog.append(KEY, "line0");
        Optional<List<String>> result = buildMapLog.get(KEY);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("line0", result.get().get(0));

        buildMapLog.append(KEY, "line1");
        result = buildMapLog.get(KEY);
        assertEquals(2, result.get().size());
        assertEquals("line0", result.get().get(0));
        assertEquals("line1", result.get().get(1));

        buildMapLog.append(KEY, "line2");
        result = buildMapLog.get(KEY);
        assertEquals(2, result.get().size());
        assertEquals("line1", result.get().get(0));
        assertEquals("line2", result.get().get(1));

        buildMapLog.append(KEY, "line3");
        result = buildMapLog.get(KEY);
        assertEquals(2, result.get().size());
        assertEquals("line2", result.get().get(0));
        assertEquals("line3", result.get().get(1));
    }

    @Test
    public void testGet() {
        buildMapLog = new BuildMapLog(5);

        buildMapLog.append(KEY, "line0");
        buildMapLog.append(KEY, "line1");
        buildMapLog.append(KEY, "line2");
        buildMapLog.append(KEY, "line3");
        buildMapLog.append(KEY, "line4");

        final Optional<List<String>> result = buildMapLog.get(KEY);
        assertTrue(result.isPresent());
        assertEquals(5, result.get().size());
        assertEquals("line0", result.get().get(0));
        assertEquals("line1", result.get().get(1));
        assertEquals("line2", result.get().get(2));
        assertEquals("line0", result.get().get(0));
        assertEquals("line1", result.get().get(1));
    }
}