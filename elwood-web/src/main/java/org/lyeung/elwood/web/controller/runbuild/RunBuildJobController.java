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

import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.IncrementBuildCountCommand;
import org.lyeung.elwood.executor.command.KeyCountTuple;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.runbuild.enums.ContentResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 4/08/2015.
 */
@RequestMapping(value = NavigationConstants.RUN_BUILD_JOB)
@RestController
public class RunBuildJobController {

    @Autowired
    private BuildExecutor buildExecutor;

    @Autowired
    private BuildMapLog<KeyCountTuple> buildMapLog;

    @Autowired
    private IncrementBuildCountCommand incrementBuildCountCommand;

    @Autowired
    private BuildResultRepository buildResultRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RunBuildJobResponse runBuildJob(@RequestBody KeyTuple keyTuple) {
        final Long count = incrementBuildCountCommand.execute(keyTuple.getKey());
        final KeyCountTuple keyCountTuple = new KeyCountTuple(keyTuple.getKey(), count);

        BuildResult buildResult = createBuildResult(keyCountTuple);
        buildResultRepository.save(buildResult);

        final Future<Integer> previousFuture = buildMapLog.addFuture(keyCountTuple,
                buildExecutor.add(keyCountTuple));

        if (previousFuture != null) {
            throw new IllegalStateException(
                    "previous future already exists in buildMapLog for key=["
                            + keyCountTuple + "]");
        }

        return new RunBuildJobResponse(keyCountTuple);
    }

    private BuildResult createBuildResult(KeyCountTuple keyCountTuple) {
        BuildResult buildResult = new BuildResult();
        buildResult.setKey(new BuildResultKey(new BuildKey(
                keyCountTuple.getKey()), keyCountTuple.getCount()));
        buildResult.setStartRunDate(new Date());
        buildResult.setBuildStatus(BuildStatus.IN_PROGRESS);

        return buildResult;
    }

    @RequestMapping(value = "/buildKeys/{key}", method = RequestMethod.GET)
    public GetBuildKeysResponse getBuildKeys(@PathVariable("key") String key) {
        return new GetBuildKeysResponse(new KeyTuple(key),
                buildMapLog.getFutureKeys().stream()
                        .filter(kc -> kc.getKey().equals(key))
                        .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{key}/{count}", method = RequestMethod.GET)
    public ContentResponse getContentByKey(
            @PathVariable("key") String key, @PathVariable("count") Long count)
            throws MalformedURLException {

        final KeyCountTuple keyCountTuple = new KeyCountTuple(key, count);
        return new ContentResponseBuilder(buildMapLog, buildResultRepository, keyCountTuple)
                .build();
    }

    /**
     * ContentResponseBuilder class builds a {@link ContentResponse} object.
     */
    private static class ContentResponseBuilder {

        private final BuildMapLog<KeyCountTuple> buildMapLog;

        private final BuildResultRepository buildResultRepository;

        private final KeyCountTuple keyCountTuple;

        public ContentResponseBuilder(BuildMapLog<KeyCountTuple> buildMapLog,
            BuildResultRepository buildResultRepository, KeyCountTuple keyCountTuple) {

            this.buildMapLog = buildMapLog;
            this.buildResultRepository = buildResultRepository;
            this.keyCountTuple = keyCountTuple;
        }

        /**
         * Builds a {@link ContentResponse} object.
         * <p/>
         * Returns {@link ContentResponse} with an empty {@link ContentResponse#content} and
         * {@link ContentResponse#status} set to the value found in
         * {@link BuildResult#buildStatus} and a populated view url when {@link Future}:
         * <ul>
         *     <li>>does not exist in {@link BuildMapLog#futureMap}</li>
         *     <li>exists and has not been completed but there is no corresponding content<br>
         *         found in {@link BuildMapLog#map}
         *     </li>
         * </ul>
         * <p/>
         * Returns {@link ContentResponse} with an empty {@link ContentResponse#content} and
         * status set to {@link ContentResponseStatus#SUCCESS} when a {@link Future} is found in
         * {@link BuildMapLog#futureMap} and has been completed.
         * <p/>
         * Returns {@link ContentResponse} with a populated of {@link ContentResponse#content} and
         * status set to {@link ContentResponseStatus#RUNNING} and view url is left as null.
         *
         * @return ContentResponse
         * @throws MalformedURLException is thrown when {@code url} format is incorrect
         */
        public ContentResponse build() throws MalformedURLException {
            final Optional<Boolean> futureDone = buildMapLog.isFutureDone(keyCountTuple);
            if (!futureDone.isPresent()) {
                return getEmptyContentResponse();
            }

            final Boolean doneFlag = futureDone.get();
            if (!doneFlag) {
                final Optional<List<String>> content = buildMapLog.getContent(keyCountTuple);
                if (content.isPresent()) {
                    List<String> lines = getLines(content);
                    return new ContentResponse(keyCountTuple, ContentResponseStatus.RUNNING,
                            lines.stream().collect(Collectors.joining()));
                }

                return getEmptyContentResponse();
            }

            return new ContentResponse(keyCountTuple, ContentResponseStatus.SUCCESS, getViewUrl());
        }

        private ContentResponse getEmptyContentResponse() throws MalformedURLException {
            final ContentResponseStatus status = getContentResponseStatus();
            return new ContentResponse(keyCountTuple, status, getViewUrl(status));
        }

        private URL getViewUrl() throws MalformedURLException {
            return getViewUrl(ContentResponseStatus.SUCCESS);
        }

        private URL getViewUrl(ContentResponseStatus status) throws MalformedURLException {
            if (status == ContentResponseStatus.SUCCESS
                    || status == ContentResponseStatus.FAILED) {
                return new URL("http://localhost:8080/viewBuildLog/" + keyCountTuple.toString());
            }

            return null;
        }

        private ContentResponseStatus getContentResponseStatus() {
            final Optional<BuildResult> buildResult = buildResultRepository.getOne(
                    new BuildResultKey(new BuildKey(keyCountTuple.getKey()),
                            keyCountTuple.getCount()));
            if (!buildResult.isPresent()) {
                return ContentResponseStatus.UNKNOWN;
            }

            if (buildResult.get().getBuildStatus() == BuildStatus.SUCCEEDED) {
                return ContentResponseStatus.SUCCESS;
            }

            if (buildResult.get().getBuildStatus() == BuildStatus.FAILED) {
                return ContentResponseStatus.FAILED;
            }

            return ContentResponseStatus.RUNNING;
        }

        private List<String> getLines(Optional<List<String>> lines) {
            if (lines.isPresent()) {
                return lines.get();
            }

            return new ArrayList<>();
        }
    }

}
