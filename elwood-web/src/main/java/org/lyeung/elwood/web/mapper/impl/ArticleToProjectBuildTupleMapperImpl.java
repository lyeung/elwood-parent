package org.lyeung.elwood.web.mapper.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class ArticleToProjectBuildTupleMapperImpl implements Mapper<Article, ProjectBuildTuple> {

    @Override
    public ProjectBuildTuple map(Article article) {
        return new ProjectBuildTuple(toProject(article), toBuild(article));
    }

    private Project toProject(Article article) {
        final Project project = new Project();
        project.setKey(article.getKey().toUpperCase());
        project.setName(article.getName());
        project.setDescription(article.getDescription());
        project.setBuildFile(article.getBuildFile());

        return project;
    }

    private Build toBuild(Article article) {
        final Build build = new Build();
        build.setBuildCommand(article.getBuildCommand());
        build.setKey(article.getKey());
        build.setEnvironmentVars(article.getEnvironmentVars());
//        build.setWorkingDirectory(article.getWorkingDirectory());

        return build;
    }
}
