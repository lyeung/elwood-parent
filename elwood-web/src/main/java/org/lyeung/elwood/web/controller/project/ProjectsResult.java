package org.lyeung.elwood.web.controller.project;

import org.lyeung.elwood.data.redis.domain.Project;

import java.util.List;

/**
 * Created by lyeung on 2/08/2015.
 */
public class ProjectsResult {

    private final List<Project> projects;

    public ProjectsResult(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
