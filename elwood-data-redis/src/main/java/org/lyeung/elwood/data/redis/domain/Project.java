package org.lyeung.elwood.data.redis.domain;

/**
 * Created by lyeung on 29/07/2015.
 */
public class Project {

    private String key;

    private String name;

    private String description;

    private String buildFile;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildFile() {
        return buildFile;
    }

    public void setBuildFile(String buildFile) {
        this.buildFile = buildFile;
    }
}
