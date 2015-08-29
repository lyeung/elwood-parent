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

package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Created by lyeung on 1/08/2015.
 */
public final class ModelStereotypeUtil {

    private ModelStereotypeUtil() {
        // do-nothing
    }

    public static Project createProject(String key) {
        Project project = new Project();
        project.setKey(key);
        project.setName("Project " + key);
        project.setDescription("Project " + key + " description");
        project.setBuildFile("pom.xml");

        return project;
    }

    public static Build createBuild(String key) {
        final Build build = new Build();
        build.setKey(key);
        build.setWorkingDirectory("workingDirectory");
        build.setBuildCommand("buildDirectory");
        build.setEnvironmentVars("environmentVars");

        return build;
    }

    public static BuildResult createBuildResult(String key) {
        final LocalDateTime finishRunDate = LocalDateTime.of(2015, Month.AUGUST, 29, 23, 30);

        final BuildResult buildResult = new BuildResult();
        buildResult.setKey(key);
        buildResult.setStartRunDate(Date.from(
                finishRunDate.minusDays(1L).atZone(ZoneId.systemDefault()).toInstant()));
        buildResult.setFinishRunDate(Date.from(
                finishRunDate.atZone(ZoneId.systemDefault()).toInstant()));
        buildResult.setBuildStatus(BuildStatus.SUCCEEDED);

        return buildResult;
    }
}
