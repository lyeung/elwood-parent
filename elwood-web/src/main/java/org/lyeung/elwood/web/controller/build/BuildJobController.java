/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.web.controller.build;

import org.lyeung.elwood.data.redis.domain.BuildKey;
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
    public BuildJob saveBuildJob(@RequestBody BuildJob buildJob) {
        return saveBuildJobCommand.execute(buildJob);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBuildJob(@PathVariable("key") String key) {
        deleteBuildJobCommand.execute(new BuildKey(key));
    }
}
