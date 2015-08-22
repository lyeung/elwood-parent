package org.lyeung.elwood.executor.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.BuildArticleCommand;
import org.lyeung.elwood.executor.command.BuildArticleCommandFactory;

/**
 * Created by lyeung on 20/08/2015.
 */
public class BuildArticleCommandFactoryImpl implements BuildArticleCommandFactory {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    private final BuildMapLog buildMapLog;

    public BuildArticleCommandFactoryImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository, BuildMapLog buildMapLog) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
        this.buildMapLog = buildMapLog;
    }

    @Override
    public BuildArticleCommand makeCommand() {
        return new BuildArticleCommandImpl(
                projectRepository, buildRepository, buildMapLog);
    }

}
