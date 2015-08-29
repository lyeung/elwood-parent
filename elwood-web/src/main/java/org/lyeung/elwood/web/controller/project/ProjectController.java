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

package org.lyeung.elwood.web.controller.project;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public Project getByKey(@PathVariable("key") String key) {
        return projectRepository.getOne(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project saveProject(@RequestBody Project project) {
        projectRepository.save(project);
        return project;
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProject(@PathVariable("key") String key) {
        projectRepository.delete(Collections.singletonList(key));
    }
}
