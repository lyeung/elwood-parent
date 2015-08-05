package org.lyeung.elwood.web.mapper.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.mapper.Mapper;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 4/08/2015.
 */
public class ProjectBuildTupleToArticleMapperImpl implements Mapper<ProjectBuildTuple, Article> {

    @Override
    public Article map(ProjectBuildTuple tuple) {
        return toArticle(tuple.getProject(), tuple.getBuild());
    }

    private Article toArticle(Project project, Build build) {
        final Article article = new Article();
        article.setKey(project.getKey());
        article.setName(project.getName());
        article.setDescription(project.getDescription());
        article.setBuildFile(project.getBuildFile());

        article.setBuildCommand(build.getBuildCommand());
        article.setEnvironmentVars(build.getEnvironmentVars());
//        article.setWorkingDirectory(build.getWorkingDirectory());
        return article;
    }

}
