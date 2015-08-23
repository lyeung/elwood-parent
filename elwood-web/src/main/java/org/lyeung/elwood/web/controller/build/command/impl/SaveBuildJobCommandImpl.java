package org.lyeung.elwood.web.controller.build.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.mapper.impl.BuildJobToProjectBuildTupleMapperImpl;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToBuildJobMapperImpl;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class SaveBuildJobCommandImpl implements SaveBuildJobCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public SaveBuildJobCommandImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public BuildJob execute(BuildJob buildJob) {
        final ProjectBuildTuple tuple =
                new BuildJobToProjectBuildTupleMapperImpl().map(buildJob);
        projectRepository.save(tuple.getProject());
        buildRepository.save(tuple.getBuild());

        return new ProjectBuildTupleToBuildJobMapperImpl().map(tuple);
    }

}
