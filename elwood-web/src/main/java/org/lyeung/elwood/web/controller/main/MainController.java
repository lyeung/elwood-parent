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

package org.lyeung.elwood.web.controller.main;

import org.lyeung.elwood.web.controller.NavigationConstants;
import org.lyeung.elwood.web.controller.main.model.AppInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyeung on 12/07/2015.
 */
@RestController
@RequestMapping("/")
public class MainController {

    @RequestMapping(value = NavigationConstants.APP_INFO, method = RequestMethod.GET)
    public AppInfo getAppInfo() {
        return new AppInfo("elwood", "1.0");
    }
}
