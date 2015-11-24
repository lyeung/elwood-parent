package org.lyeung.elwood.maven.impl;

import org.apache.maven.surefire.report.ReportEntry;
import org.apache.maven.surefire.report.RunListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyeung on 13/11/2015.
 */
public class ElwoodRunListener implements RunListener {

    private final AtomicInteger successCount = new AtomicInteger();

    private final AtomicInteger failedCount = new AtomicInteger();

    private final AtomicInteger errorCount = new AtomicInteger();

    @Override
    public void testSetStarting(ReportEntry reportEntry) {
        System.out.printf("starting " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testSetCompleted(ReportEntry reportEntry) {
        System.out.printf("completed " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testStarting(ReportEntry reportEntry) {
        System.out.printf("starting " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testSucceeded(ReportEntry reportEntry) {
        successCount.incrementAndGet();
        System.out.printf("succeeded " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testAssumptionFailure(ReportEntry reportEntry) {
        System.out.printf("succeeded " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testError(ReportEntry reportEntry) {
        System.out.printf("error " + reportEntry.getSourceName() + "." + reportEntry.getName());
        errorCount.incrementAndGet();
    }

    @Override
    public void testFailed(ReportEntry reportEntry) {
        System.out.printf("failed " + reportEntry.getSourceName() + "." + reportEntry.getName());
        failedCount.incrementAndGet();
    }

    @Override
    public void testSkipped(ReportEntry reportEntry) {
        System.out.printf("skipped " + reportEntry.getSourceName() + "." + reportEntry.getName());
    }

    @Override
    public void testExecutionSkippedByUser() {
        System.out.printf("skipped by user");
    }
}
