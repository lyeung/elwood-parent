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

package org.lyeung.elwood.web.controller.build.command.impl;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 3/08/2015.
 */
public class DeleteBuildJobCommandImpl implements DeleteBuildJobCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public DeleteBuildJobCommandImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public String execute(String key) {
        final Optional<Project> project = projectRepository.getOne(key);
        if (project.isPresent()) {
            final List<String> keyList = Collections.singletonList(
                    project.get().getKey());

            projectRepository.delete(keyList);
            buildRepository.delete(keyList);
        }

        return key;
    }
}
