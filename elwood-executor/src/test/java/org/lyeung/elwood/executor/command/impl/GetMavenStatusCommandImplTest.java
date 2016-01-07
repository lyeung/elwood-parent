package org.lyeung.elwood.executor.command.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandParamBuilder;
import org.lyeung.elwood.executor.command.MavenStatusRuleMatcherManager;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lyeung on 3/01/2016.
 */
@Category(SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetMavenStatusCommandImplTest {

    private static final String KEY = "PRJ";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private final MavenStatusRuleMatcherManager matcherManager = new MavenStatusRuleMatcherManagerImpl();

    private GetMavenStatusCommandImpl impl;

    @Test
    public void testExecute() throws IOException {
        final File checkedOutDir = temporaryFolder.newFolder("checked-out-dir");
        final File testResultDir = temporaryFolder.newFolder("checked-out-dir", "test-results");

        FileUtils.copyFile(new File("src/test/resources/maven-status-rule-matcher.elwood.result"),
                new File(testResultDir, "file1.elwood.result"));
        FileUtils.copyFile(new File("src/test/resources/maven-status-rule-matcher.elwood.result"),
                new File(testResultDir, "file2.elwood.result"));

        impl = new GetMavenStatusCommandImpl(matcherManager);

        final GetMavenStatusCommandParamBuilder builder = new GetMavenStatusCommandParamBuilder()
                .buildResultKey(new BuildResultKey(new BuildKey(KEY), 10))
                .checkedOutDir(checkedOutDir);
        final BuildResultMavenStats buildResultMavenStats = impl.execute(builder.build());
        assertNotNull(buildResultMavenStats);
        assertEquals(6, buildResultMavenStats.getSuccessCount());
        assertEquals(4, buildResultMavenStats.getFailedCount());
        assertEquals(2, buildResultMavenStats.getIgnoredCount());
    }
}