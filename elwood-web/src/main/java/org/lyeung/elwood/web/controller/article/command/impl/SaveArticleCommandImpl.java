package org.lyeung.elwood.web.controller.article.command.impl;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.article.command.SaveArticleCommand;
import org.lyeung.elwood.web.mapper.impl.ArticleToProjectBuildTupleMapperImpl;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToArticleMapperImpl;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class SaveArticleCommandImpl implements SaveArticleCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public SaveArticleCommandImpl(ProjectRepository projectRepository,
        BuildRepository buildRepository) {

        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public Article execute(Article article) {
        final ProjectBuildTuple tuple = new ArticleToProjectBuildTupleMapperImpl().map(article);
        projectRepository.save(tuple.getProject());
        buildRepository.save(tuple.getBuild());

        return new ProjectBuildTupleToArticleMapperImpl().map(tuple);
    }

}
