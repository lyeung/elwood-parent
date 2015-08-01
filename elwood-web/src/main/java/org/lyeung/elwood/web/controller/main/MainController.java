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
