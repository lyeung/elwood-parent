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
import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.executor.command.GetMavenStatusCommand;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandException;
import org.lyeung.elwood.executor.command.GetMavenStatusCommandParam;
import org.lyeung.elwood.executor.command.MavenStats;
import org.lyeung.elwood.executor.command.MavenStatusRuleMatcherManager;
import org.lyeung.elwood.executor.command.impl.enums.MavenStatusType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandImpl implements GetMavenStatusCommand {

    private final MavenStatusRuleMatcherManager matcherManager;

    public GetMavenStatusCommandImpl(MavenStatusRuleMatcherManager matcherManager) {
        this.matcherManager = matcherManager;
    }

    @Override
    public BuildResultMavenStats execute(GetMavenStatusCommandParam param) {
        final File testResultDir = new File(param.getCheckedOutDir(), "test-results");
        final String[] files = testResultDir.list((File dir, String name) ->
                name.endsWith(".elwood.result"));

        MavenStats mavenStats = new MavenStats(0, 0, 0);
        if (files != null) {
            mavenStats = Arrays.asList(files).stream()
                    .map(f -> readMavenStatsFile(new File(new File(param.getCheckedOutDir(), "test-results"), f)))
                    .reduce(mavenStats, (m1, m2) -> new MavenStats(m1.getSuccessCount() + m2.getSuccessCount(),
                            m1.getFailureCount() + m2.getFailureCount(), m1.getIgnoreCount() + m2.getIgnoreCount()));
        }

        final BuildResultMavenStats stats = new BuildResultMavenStats();
        stats.setFailedCount(mavenStats.getFailureCount());
        stats.setIgnoredCount(mavenStats.getIgnoreCount());
        stats.setSuccessCount(mavenStats.getSuccessCount());
        stats.setKey(param.getBuildResultKey());

        return stats;
    }

    private MavenStats readMavenStatsFile(File file) {
        try {
            final String content = FileUtils.readFileToString(file);
            final String[] lines = content.split("\n");

            final Map<MavenStatusType, Tuple<MavenStatusType, Integer>> result =
                    matcherManager.processLines(Arrays.asList(lines));

            return new MavenStats(result.get(MavenStatusType.SUCCESS).getValue2(),
                    result.get(MavenStatusType.FAILED).getValue2(),
                    result.get(MavenStatusType.IGNORED).getValue2());
        } catch (IOException e) {
            throw new GetMavenStatusCommandException("unable to read file=["
                    + file.getAbsolutePath() + "]", e, file);
        }
    }
}
