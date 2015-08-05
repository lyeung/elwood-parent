package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;

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

}
