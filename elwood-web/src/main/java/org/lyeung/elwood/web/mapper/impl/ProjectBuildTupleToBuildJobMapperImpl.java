package org.lyeung.elwood.web.mapper.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.vcs.command.CloneCommandParam;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class ProjectBuildTupleToBuildJobMapperImpl
        implements Mapper<ProjectBuildTuple, BuildJob> {

    @Override
    public BuildJob map(ProjectBuildTuple tuple) {
        return toBuildJob(tuple.getProject(), tuple.getBuild());
    }

    private BuildJob toBuildJob(Project project, Build build) {
        final BuildJob buildJob = new BuildJob();
        buildJob.setKey(project.getKey());
        buildJob.setName(project.getName());
        buildJob.setDescription(project.getDescription());
        buildJob.setBuildFile(project.getBuildFile());
        buildJob.setSourceUrl(project.getSourceUrl());
        buildJob.setAuthenticationType(CloneCommandParam.AuthenticationType
                .valueOf(project.getAuthenticationType()));
        buildJob.setIdentityKey(project.getIdentityKey());
        buildJob.setPassphrase(project.getPassphrase());

        buildJob.setBuildCommand(build.getBuildCommand());
        buildJob.setEnvironmentVars(build.getEnvironmentVars());

        return buildJob;
    }

}
