package org.lyeung.elwood.web.controller.project;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by lyeung on 1/08/2015.
 */
@RestController
@RequestMapping(value = NavigationConstants.PROJECT)
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public Project getByKey(@RequestParam("key") String key) {
        return projectRepository.getOne(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project saveProject(@RequestBody Project project) {
        projectRepository.save(project);
        return project;
    }

    @RequestMapping(value = "/key", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProject(@RequestParam("key") String key) {
        projectRepository.delete(Collections.singletonList(key));
    }
}
