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

package org.lyeung.elwood.web.controller.runbuild;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lyeung.elwood.common.test.QuickTest;
import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.IncrementBuildCountCommand;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.buildresult.GetBuildResultResponse;
import org.lyeung.elwood.web.controller.runbuild.enums.ContentResponseStatus;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 22/08/2015.
 */
@Category(QuickTest.class)
@RunWith(MockitoJUnitRunner.class)
public class RunBuildJobControllerTest {

    private static final String KEY = "KEY";

    private static final long COUNT = 2L;

    private static final KeyCountTuple KEY_COUNT_TUPLE = new KeyCountTuple(KEY, COUNT);

    @InjectMocks
    private RunBuildJobController controller;

    @Mock
    private BuildExecutor buildExecutor;

    @Mock
    private BuildMapLog<KeyCountTuple> buildMapLog;

    @Mock
    private BuildResultRepository buildResultRepository;

    @Mock
    private IncrementBuildCountCommand incrementBuildCountCommand;

    @Mock
    private Future<Integer> future;

    @Test
    public void testRunBuildJob() {
        when(incrementBuildCountCommand.execute(KEY)).thenReturn(2L);
        when(buildExecutor.add(KEY_COUNT_TUPLE)).thenReturn(future);
        when(buildMapLog.addFuture(KEY_COUNT_TUPLE, future)).thenReturn(null);

        final KeyTuple keyTuple = new KeyTuple("KEY");

        final RunBuildJobResponse response = controller.runBuildJob(keyTuple);
        assertEquals(KEY_COUNT_TUPLE, response.getKeyCountTuple());
        assertTrue(assertGetBuildResultResponse(response.getBuildResultResponse()));

        verify(incrementBuildCountCommand).execute(eq(KEY));
        verify(buildResultRepository).save(argThat(new ArgumentMatcher<BuildResult>() {
            @Override
            public boolean matches(Object argument) {
                final BuildResult buildResult = (BuildResult) argument;
                return assertBuildResult(buildResult);
            }
        }));
        verify(buildMapLog).addFuture(eq(KEY_COUNT_TUPLE), eq(future));
        verify(buildExecutor).add(eq(KEY_COUNT_TUPLE));
        verifyNoMoreInteractions(buildExecutor);
        verifyNoMoreInteractions(buildMapLog);
        verifyZeroInteractions(buildResultRepository);
    }

    private boolean assertGetBuildResultResponse(GetBuildResultResponse buildResultResponse) {
        return buildResultResponse.getKeyCountTuple().equals(new KeyCountTuple(KEY, COUNT))
                && buildResultResponse.getBuildStatus() == BuildStatus.IN_PROGRESS
                && buildResultResponse.getStartRunDate() != null
                && buildResultResponse.getFinishRunDate() == null;
    }

    private boolean assertBuildResult(BuildResult buildResult) {
        return buildResult.getKey().equals(new BuildResultKey(new BuildKey(KEY), COUNT))
                        && buildResult.getBuildStatus() == BuildStatus.IN_PROGRESS
                        && buildResult.getStartRunDate() != null
                        && buildResult.getFinishRunDate() == null;
    }

    @Test
    public void testGetContentByKey() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(of(false));
        when(buildMapLog.getContent(KEY_COUNT_TUPLE)).thenReturn(
                of(Arrays.asList("hello", "world")));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.RUNNING, response.getStatus());
        assertEquals("helloworld", response.getContent());
        assertNull(response.getRedirectUrl());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildMapLog).getContent(eq(KEY_COUNT_TUPLE));
        verifyNoMoreInteractions(buildMapLog);
        verifyNoMoreInteractions(buildResultRepository);
    }

    @Test
    public void testGetEmptyContentByKey() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(of(false));
        when(buildMapLog.getContent(KEY_COUNT_TUPLE)).thenReturn(empty());

        final BuildResult buildResult = createBuildResult(KEY_COUNT_TUPLE, BuildStatus.IN_PROGRESS);
        final BuildResultKey key = new BuildResultKey(
                new BuildKey(KEY_COUNT_TUPLE.getKey()), KEY_COUNT_TUPLE.getCount());
        when(buildResultRepository.getOne(key))
                .thenReturn(of(buildResult));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.RUNNING, response.getStatus());
        assertNull(response.getContent());
        assertNull(response.getRedirectUrl());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildMapLog).getContent(eq(KEY_COUNT_TUPLE));
        verify(buildResultRepository).getOne(eq(key));
        verifyNoMoreInteractions(buildMapLog);
        verifyNoMoreInteractions(buildResultRepository);
    }

    @Test
    public void testGetUnknownContentByKey() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(empty());
        final BuildResultKey key = new BuildResultKey(new BuildKey(KEY_COUNT_TUPLE.getKey()),
                KEY_COUNT_TUPLE.getCount());
        when(buildResultRepository.getOne(key)).thenReturn(empty());

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.UNKNOWN, response.getStatus());
        assertNull(response.getContent());
        assertNull(response.getRedirectUrl());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildResultRepository).getOne(eq(key));
        verifyNoMoreInteractions(buildMapLog);
        verifyNoMoreInteractions(buildResultRepository);
    }

    @Test
    public void testGetUnknownContentByKeyAfterSuccess() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(empty());

        final BuildResult buildResult = createBuildResult(KEY_COUNT_TUPLE, BuildStatus.SUCCEEDED);
        final BuildResultKey key = new BuildResultKey(
                new BuildKey(KEY_COUNT_TUPLE.getKey()), KEY_COUNT_TUPLE.getCount());
        when(buildResultRepository.getOne(key))
                .thenReturn(of(buildResult));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.SUCCESS, response.getStatus());
        assertNull(response.getContent());
        assertEquals("http://localhost:8080/viewBuildLog/" + KEY_COUNT_TUPLE,
                response.getRedirectUrl().toExternalForm());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildResultRepository).getOne(eq(key));
        verifyNoMoreInteractions(buildMapLog);
        verifyNoMoreInteractions(buildResultRepository);
    }

    @Test
    public void testGetUnknownContentByKeyAfterFailed() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(empty());

        final BuildResult buildResult = createBuildResult(KEY_COUNT_TUPLE, BuildStatus.FAILED);
        final BuildResultKey key = new BuildResultKey(
                new BuildKey(KEY_COUNT_TUPLE.getKey()), KEY_COUNT_TUPLE.getCount());
        when(buildResultRepository.getOne(key))
                .thenReturn(of(buildResult));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.FAILED, response.getStatus());
        assertNull(response.getContent());
        assertEquals("http://localhost:8080/viewBuildLog/" + KEY_COUNT_TUPLE,
                response.getRedirectUrl().toExternalForm());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildResultRepository).getOne(eq(key));
        verifyNoMoreInteractions(buildMapLog);
        verifyNoMoreInteractions(buildResultRepository);
    }

    @Test
    public void testGetTransitionToDoneContentByKey() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(of(false));
        when(buildMapLog.getContent(KEY_COUNT_TUPLE)).thenReturn(empty());

        final BuildResult buildResult = createBuildResult(KEY_COUNT_TUPLE, BuildStatus.FAILED);
        final BuildResultKey key = new BuildResultKey(
                new BuildKey(KEY_COUNT_TUPLE.getKey()), KEY_COUNT_TUPLE.getCount());
        when(buildResultRepository.getOne(key))
                .thenReturn(of(buildResult));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.FAILED, response.getStatus());
        assertNull(response.getContent());
        assertEquals("http://localhost:8080/viewBuildLog/" + KEY_COUNT_TUPLE,
                response.getRedirectUrl().toExternalForm());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verify(buildMapLog).getContent(KEY_COUNT_TUPLE);
        verify(buildResultRepository).getOne(eq(key));

        verifyZeroInteractions(buildMapLog);
        verifyZeroInteractions(buildResultRepository);
    }

    @Test
    public void testGetDoneContentByKey() throws MalformedURLException {
        when(buildMapLog.isFutureDone(KEY_COUNT_TUPLE)).thenReturn(of(true));

        final ContentResponse response = controller.getContentByKey(KEY, COUNT);
        assertEquals(ContentResponseStatus.SUCCESS, response.getStatus());
        assertNull(response.getContent());
        assertEquals("http://localhost:8080/viewBuildLog/" + KEY_COUNT_TUPLE,
                response.getRedirectUrl().toExternalForm());

        verify(buildMapLog).isFutureDone(eq(KEY_COUNT_TUPLE));
        verifyNoMoreInteractions(buildMapLog);
        verifyZeroInteractions(buildResultRepository);
    }

    @Test
    public void testGetFutureKeys() {
        final List<KeyCountTuple> futureKeys = Arrays.asList(
                new KeyCountTuple("ELWP", 10L),
                new KeyCountTuple("ELWP", 20L),
                new KeyCountTuple("ELWUI", 20L));
        when(buildMapLog.getFutureKeys()).thenReturn(futureKeys);

        final GetBuildKeysResponse result = controller.getBuildKeys("ELWP");
        assertEquals("ELWP", result.getKey().getKey());
        assertEquals(2, result.getKeyCounts().size());
        assertTrue(result.getKeyCounts().contains(new KeyCountTuple("ELWP", 10L)));
        assertTrue(result.getKeyCounts().contains(new KeyCountTuple("ELWP", 20L)));
    }

    private BuildResult createBuildResult(KeyCountTuple keyCountTuple, BuildStatus status) {
        final BuildResult buildResult = new BuildResult();
        buildResult.setBuildStatus(status);
        buildResult.setStartRunDate(new Date());
        buildResult.setFinishRunDate(new Date());
        buildResult.setKey(new BuildResultKey(new BuildKey(keyCountTuple.getKey()),
                keyCountTuple.getCount()));

        return buildResult;
    }

}