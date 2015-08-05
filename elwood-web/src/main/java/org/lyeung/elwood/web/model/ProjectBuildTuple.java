package org.lyeung.elwood.web.model;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;

/**
 * Created by lyeung on 4/08/2015.
 */
public class ProjectBuildTuple {

    private Project project;

    private Build build;

    public ProjectBuildTuple(Project project, Build build) {
        this.project = project;
        this.build = build;
    }

    public Project getProject() {
        return project;
    }

    public Build getBuild() {
        return build;
    }
}
