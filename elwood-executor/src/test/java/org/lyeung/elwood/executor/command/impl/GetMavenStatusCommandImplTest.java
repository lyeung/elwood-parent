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