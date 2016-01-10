package org.lyeung.elwood.web.controller.buildstats;

/**
 * Created by lyeung on 10/01/2016.
 */
public class BuildResultStats {

    public static final BuildResultStats NONE = new BuildResultStats(-1, -1, -1);

    private final int successCount;

    private final int failedCount;

    private final int ignoredCount;

    public BuildResultStats(int successCount, int failedCount, int ignoredCount) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.ignoredCount = ignoredCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public int getIgnoredCount() {
        return ignoredCount;
    }
}
