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
