package org.lyeung.elwood.web.controller.article.command.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.article.command.GetArticleCommand;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToArticleMapperImpl;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class GetArticleCommandImpl implements GetArticleCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public GetArticleCommandImpl(ProjectRepository projectRepository, BuildRepository buildRepository) {
        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public Article execute(String key) {
        final Project project = projectRepository.getOne(key);
        if (project == null) {
            return null;
        }

        final Build build = buildRepository.getOne(key);

        return new ProjectBuildTupleToArticleMapperImpl().map(new ProjectBuildTuple(project, build));
    }
}
