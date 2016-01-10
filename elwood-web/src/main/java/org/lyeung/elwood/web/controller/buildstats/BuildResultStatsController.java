package org.lyeung.elwood.web.controller.buildstats;

import org.lyeung.elwood.data.redis.domain.BuildKey;
import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.buildstats.command.GetBuildResultStatsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 10/01/2016.
 */
@RequestMapping(value = NavigationConstants.BUILD_RESULT_STATS)
@RestController
public class BuildResultStatsController {

    @Autowired
    private GetBuildResultStatsCommand buildResultStatsCommand;

    @RequestMapping(value = "/{key}/{count}", method = RequestMethod.GET)
    public BuildResultStats getBuildResultStats(
            @PathVariable("key") String key, @PathVariable("count") int count) {
        return buildResultStatsCommand.execute(new BuildResultKey(new BuildKey(key), count));
    }
}
