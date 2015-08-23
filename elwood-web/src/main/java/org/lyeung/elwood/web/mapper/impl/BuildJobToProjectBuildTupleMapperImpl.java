package org.lyeung.elwood.web.mapper.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.BuildJob;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class BuildJobToProjectBuildTupleMapperImpl implements Mapper<BuildJob, ProjectBuildTuple> {

    @Override
    public ProjectBuildTuple map(BuildJob buildJob) {
        return new ProjectBuildTuple(toProject(buildJob), toBuild(buildJob));
    }

    private Project toProject(BuildJob buildJob) {
        final Project project = new Project();
        project.setKey(buildJob.getKey().toUpperCase());
        project.setName(buildJob.getName());
        project.setDescription(buildJob.getDescription());
        project.setBuildFile(buildJob.getBuildFile());
        project.setSourceUrl(buildJob.getSourceUrl());
        project.setAuthenticationType(buildJob.getAuthenticationType().name());
        project.setIdentityKey(buildJob.getIdentityKey());
        project.setPassphrase(buildJob.getPassphrase());

        return project;
    }

    private Build toBuild(BuildJob buildJob) {
        final Build build = new Build();
        build.setBuildCommand(buildJob.getBuildCommand());
        build.setKey(buildJob.getKey().toUpperCase());
        build.setEnvironmentVars(buildJob.getEnvironmentVars());
        build.setWorkingDirectory(buildJob.getKey().toUpperCase());

        return build;
    }
}
