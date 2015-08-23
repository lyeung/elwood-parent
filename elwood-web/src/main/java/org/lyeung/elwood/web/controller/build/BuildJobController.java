package org.lyeung.elwood.web.controller.build;

import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.GetBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.model.BuildJob;
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
@RequestMapping(NavigationConstants.BUILD_JOB)
@RestController
public class BuildJobController {

    @Autowired
    private GetBuildJobCommand getBuildJobCommand;

    @Autowired
    private SaveBuildJobCommand saveBuildJobCommand;

    @Autowired
    private DeleteBuildJobCommand deleteBuildJobCommand;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public BuildJob getByKey(@PathVariable("key") String key) {
        return getBuildJobCommand.execute(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BuildJob saveArticle(@RequestBody BuildJob buildJob) {
        return saveBuildJobCommand.execute(buildJob);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProject(@PathVariable("key") String key) {
        deleteBuildJobCommand.execute(key);
    }
}
