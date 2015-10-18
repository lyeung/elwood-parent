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

package org.lyeung.elwood.web.controller.buildresult.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.SlowTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultResponse;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultsResponse;
import org.lyeung.elwood.web.controller.runbuild.KeyTuple;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 6/10/2015.
 */
@Category(SlowTest.class)
@RunWith(MockitoJUnitRunner.class)
public class GetBuildResultCommandImplTest {

    private static final String KEY = "ELWP";

    private GetBuildResultCommandImpl impl;

    @Mock
    private BuildResultRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        impl = new GetBuildResultCommandImpl(repository);
    }

    @Test
    public void testExecute() {
        when(repository.findAll(new BuildKey(KEY), 0L, 5L)).thenReturn(
                Arrays.asList(createBuildResult(new BuildResultKey(new BuildKey(KEY), 10L),
                                BuildStatus.SUCCEEDED),
                        createBuildResult(new BuildResultKey(new BuildKey(KEY), 20L),
                                BuildStatus.IN_PROGRESS)));

        final GetBuildResultsResponse response = impl.execute(new KeyTuple(KEY));
        assertEquals(new KeyTuple(KEY), response.getKeyTuple());
        assertEquals(2, response.getBuildResultResponses().size());

        GetBuildResultResponse result = response.getBuildResultResponses().get(0);
        assertEquals(BuildStatus.SUCCEEDED, result.getBuildStatus());
        assertEquals(new KeyCountTuple(KEY, 10L), result.getKeyCountTuple());
        assertNotNull(result.getStartRunDate());
        assertNotNull(result.getFinishRunDate());

        result = response.getBuildResultResponses().get(1);
        assertEquals(BuildStatus.IN_PROGRESS, result.getBuildStatus());
        assertEquals(new KeyCountTuple(KEY, 20L), result.getKeyCountTuple());
        assertNotNull(result.getStartRunDate());
        assertNotNull(result.getFinishRunDate());

        verify(repository).findAll(eq(new BuildKey(KEY)), eq(0L), eq(5L));
        verifyNoMoreInteractions(repository);
    }

    private BuildResult createBuildResult(BuildResultKey buildResultKey, BuildStatus status) {
        final BuildResult buildResult = new BuildResult();
        buildResult.setKey(buildResultKey);
        buildResult.setBuildStatus(status);
        buildResult.setStartRunDate(new Date());
        buildResult.setFinishRunDate(new Date());

        return buildResult;
    }
}