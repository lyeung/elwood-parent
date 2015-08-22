package org.lyeung.elwood.web.controller.build;

import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 4/08/2015.
 */
@RequestMapping(value = NavigationConstants.RUN_ARTICLE)
@RestController
public class BuildArticleController {

    @Autowired
    private BuildExecutor buildExecutor;

    @Autowired
    private BuildMapLog buildMapLog;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void buildArticle(@RequestBody KeyTuple keyTuple) {
        buildExecutor.add(keyTuple.getKey());
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ContentResponse getContentByKey(@PathVariable("key") String key) {
        final Optional<List<String>> line = buildMapLog.get(key);
        if (line.isPresent()) {
            return new ContentResponse(line.get().stream().collect(Collectors.joining()));
        }

        return ContentResponse.EMPTY;
    }
}
