package org.lyeung.elwood.web.controller.article.command.impl;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.article.command.SaveArticleCommand;
import org.lyeung.elwood.web.model.Article;
import org.lyeung.elwood.web.mapper.impl.ProjectBuildTupleToArticleMapperImpl;
import org.lyeung.elwood.web.model.ProjectBuildTuple;

/**
 * Created by lyeung on 3/08/2015.
 */
public class SaveArticleCommandImpl implements SaveArticleCommand {

    private final ProjectRepository projectRepository;

    private final BuildRepository buildRepository;

    public SaveArticleCommandImpl(ProjectRepository projectRepository, BuildRepository buildRepository) {
        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
    }

    @Override
    public Article execute(Article article) {
        final Project project = toProject(article);
        projectRepository.save(project);

        final Build build = toBuild(article);
        buildRepository.save(build);

        return new ProjectBuildTupleToArticleMapperImpl().map(new ProjectBuildTuple(project, build));
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
        build.setWorkingDirectory(article.getKey().toUpperCase());

        return build;
    }
}
