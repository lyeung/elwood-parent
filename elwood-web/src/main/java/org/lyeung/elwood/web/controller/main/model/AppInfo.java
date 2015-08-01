package org.lyeung.elwood.web.controller.main.model;

/**
 * Created by lyeung on 12/07/2015.
 */
public class AppInfo {

    private String appName;

    private String version;

    public AppInfo(String appName, String version) {
        this.appName = appName;
        this.version = version;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }
}
