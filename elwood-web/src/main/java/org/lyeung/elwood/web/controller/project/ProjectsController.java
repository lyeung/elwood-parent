package org.lyeung.elwood.web.controller.project;

import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 2/08/2015.
 */
@RestController
@RequestMapping(value = NavigationConstants.PROJECTS)
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ProjectsResult getAllProjects() {
        return new ProjectsResult(projectRepository.findAll());
    }
}
