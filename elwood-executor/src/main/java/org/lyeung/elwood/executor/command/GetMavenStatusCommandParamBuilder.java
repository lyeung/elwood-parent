package org.lyeung.elwood.executor.command;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;

import java.io.File;

/**
 * Created by lyeung on 3/01/2016.
 */
public class GetMavenStatusCommandParamBuilder {

    private File checkedOutDir;

    private BuildResultKey buildResultKey;

    public GetMavenStatusCommandParamBuilder checkedOutDir(File checkedOutDir) {
        this.checkedOutDir = checkedOutDir;
        return this;
    }

    public GetMavenStatusCommandParamBuilder buildResultKey(BuildResultKey buildResultKey) {
        this.buildResultKey = buildResultKey;
        return this;
    }

    public GetMavenStatusCommandParam build() {
        final GetMavenStatusCommandParam param = new GetMavenStatusCommandParam();
        param.setBuildResultKey(buildResultKey);
        param.setCheckedOutDir(checkedOutDir);

        return param;
    }
}
