package org.lyeung.elwood.web.controller.build.command.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.build.command.GetBuildJobCommand;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToBuildJobMapperImpl;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class GetBuildJobCommandImpl implements GetBuildJobCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public GetBuildJobCommandImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public BuildJob execute(String key) {
        final Project project = projectRepository.getOne(key);
        if (project == null) {
            return null;
        }

        final Build build = buildRepository.getOne(key);

        return new ProjectBuildTupleToBuildJobMapperImpl().map(
                new ProjectBuildTuple(project, build));
    }
}
