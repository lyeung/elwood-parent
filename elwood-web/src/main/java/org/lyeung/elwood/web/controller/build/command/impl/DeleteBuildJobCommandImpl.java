package org.lyeung.elwood.web.controller.build.command.impl;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;

import java.util.Collections;
import java.util.List;

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
        final Project project = projectRepository.getOne(key);
        if (project != null) {
            final List<String> keyList = Collections.singletonList(
                    project.getKey());

            projectRepository.delete(keyList);
            buildRepository.delete(keyList);
        }

        return key;
    }
}
