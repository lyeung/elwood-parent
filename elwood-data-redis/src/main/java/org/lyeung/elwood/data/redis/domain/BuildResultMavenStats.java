package org.lyeung.elwood.data.redis.domain;

/**
 * Created by lyeung on 25/12/2015.
 */
public class BuildResultMavenStats extends AbstractDomain<BuildResultKey> {

    private int successCount;

    private int failedCount;

    private int ignoredCount;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getIgnoredCount() {
        return ignoredCount;
    }

    public void setIgnoredCount(int ignoredCount) {
        this.ignoredCount = ignoredCount;
    }
}
