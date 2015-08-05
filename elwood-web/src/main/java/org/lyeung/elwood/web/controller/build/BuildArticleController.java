package org.lyeung.elwood.web.controller.build;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 4/08/2015.
 */
@RequestMapping(value = NavigationConstants.RUN_ARTICLE)
@RestController
public class BuildArticleController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BuildRepository buildRepository;

    @RequestMapping(value = "/{key}", method = RequestMethod.POST)
    public void buildArticle(@PathVariable("key") String key) {
        final Project project = projectRepository.getOne(key);
        final Build build = buildRepository.getOne(key);

        // build here
    }
}
