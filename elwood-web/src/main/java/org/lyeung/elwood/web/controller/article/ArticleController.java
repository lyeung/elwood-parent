package org.lyeung.elwood.web.controller.article;

import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.article.command.DeleteArticleCommand;
import org.lyeung.elwood.web.controller.article.command.GetArticleCommand;
import org.lyeung.elwood.web.controller.article.command.SaveArticleCommand;
import org.lyeung.elwood.web.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 3/08/2015.
 */
@RequestMapping(NavigationConstants.ARICLE)
@RestController
public class ArticleController {

    @Autowired
    private GetArticleCommand getArticleCommand;

    @Autowired
    private SaveArticleCommand saveArticleCommand;

    @Autowired
    private DeleteArticleCommand deleteArticleCommand;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public Article getByKey(@PathVariable("key") String key) {
        return getArticleCommand.execute(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Article saveArticle(@RequestBody Article article) {
        return saveArticleCommand.execute(article);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProject(@PathVariable("key") String key) {
        deleteArticleCommand.execute(key);
    }
}
