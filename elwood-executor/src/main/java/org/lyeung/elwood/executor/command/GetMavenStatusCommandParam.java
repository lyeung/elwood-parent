package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;

import java.io.File;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandParam {

    private File checkedOutDir;

    private BuildResultKey buildResultKey;

    public File getCheckedOutDir() {
        return checkedOutDir;
    }

    public void setCheckedOutDir(File checkedOutDir) {
        this.checkedOutDir = checkedOutDir;
    }

    public BuildResultKey getBuildResultKey() {
        return buildResultKey;
    }

    public void setBuildResultKey(BuildResultKey buildResultKey) {
        this.buildResultKey = buildResultKey;
    }
}
