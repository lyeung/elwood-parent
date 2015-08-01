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

    public static Project createProject(String code) {
        Project project = new Project();
        project.setKey("PRJ-" + code);
        project.setName("Project " + code);
        project.setDescription("Project " + code + " description");
        project.setBuildFile("pom.xml");
        return project;
    }

    public static Build createBuild(String code) {
        final Build build = new Build();
        build.setWorkingDirectory("workingDirectory");
        build.setBuildCommand("buildDirectory");
        build.setEnvironmentVars("environmentVars");
        build.setProject(createProject(code));
        return build;
    }

}
