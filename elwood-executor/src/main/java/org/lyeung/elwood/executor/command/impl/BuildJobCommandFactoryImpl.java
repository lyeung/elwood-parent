package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.BuildJobCommand;
import org.lyeung.elwood.executor.command.BuildJobCommandFactory;

/**
 * Created by lyeung on 20/08/2015.
 */
public class BuildJobCommandFactoryImpl implements BuildJobCommandFactory {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    private final BuildMapLog buildMapLog;

    public BuildJobCommandFactoryImpl(ProjectRepository projectRepository,
                                      BuildRepository buildRepository, BuildMapLog buildMapLog) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
        this.buildMapLog = buildMapLog;
    }

    @Override
    public BuildJobCommand makeCommand() {
        return new BuildJobCommandImpl(
                projectRepository, buildRepository, buildMapLog);
    }

}
