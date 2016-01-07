package org.lyeung.elwood.executor.command;

/**
 * Created by lyeung on 3/01/2016.
 */
public final class MavenStats {

    private final int successCount;

    private final int failureCount;

    private final int ignoreCount;

    public MavenStats(int successCount, int failureCount, int ignoreCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.ignoreCount = ignoreCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getIgnoreCount() {
        return ignoreCount;
    }
}
