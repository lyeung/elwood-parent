package org.lyeung.elwood.maven.runlistener;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 13/11/2015.
 */
public class ElwoodRunListener extends RunListener {

    private enum Status { SUCCESS, FAILED, IGNORED }

    private static final String ELWOOD_RUN_LISTENER_RESULT_FILE = ".elwood.result";

    private final Map<String, Status> resultMap = new HashMap<>();

    private String filename;

    @Override
    public void testRunFinished(Result result) throws Exception {
        final Map<Status, List<Map.Entry<String, Status>>> groupBy = resultMap.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue()));

        final int ignored = getCount(groupBy, Status.IGNORED);
        if (ignored != result.getIgnoreCount()) {
            throw new IllegalArgumentException("expecting ignored count=["
                    + result.getIgnoreCount()
                    + " but was ["
                    + ignored + "]");
        }

        final int failed = getCount(groupBy, Status.FAILED);
        if (failed != result.getFailureCount()) {
            throw new IllegalArgumentException("expecting failure count=["
                    + result.getFailureCount()
                    + "] but was ["
                    + failed + "]");
        }

        final int success = getCount(groupBy, Status.SUCCESS);
        final int expectedSuccess = result.getRunCount();
        if (success != expectedSuccess) {
            throw new IllegalArgumentException("expecting success count=["
                    + expectedSuccess
                    + "] but was ["
                    + success + "]");
        }


        String content = "success: " + getMethodNames(groupBy, Status.SUCCESS) + "\n"
                + "failed: " + getMethodNames(groupBy, Status.FAILED) + "\n"
                + "ignored: " + getMethodNames(groupBy, Status.IGNORED);

        writeResult(content);
    }

    private void writeResult(String content) throws IOException {
        File targetDir = new File(System.getenv("ELWOOD_BUILD_DIR"), "test-results");

        // TODO: add result check here
        targetDir.mkdir();

        final File resultFile = new File(targetDir,
                filename + ELWOOD_RUN_LISTENER_RESULT_FILE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw e;
        }
    }

    @Override
    public void testStarted(Description description) throws Exception {
        resultMap.put(getClassMethodName(description), Status.SUCCESS);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        filename = description.getTestClass().getCanonicalName();
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        resultMap.put(getClassMethodName(failure.getDescription()), Status.FAILED);
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        resultMap.put(getClassMethodName(failure.getDescription()), Status.FAILED);
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        resultMap.put(getClassMethodName(description), Status.IGNORED);
    }

    private String getClassMethodName(Description description) {
        return description.getTestClass().getCanonicalName() + "." + description.getMethodName();
    }

    private List<String> getMethodNames(
            Map<Status, List<Map.Entry<String, Status>>> groupBy, Status status) {
        final List<Map.Entry<String, Status>> list = groupBy.get(status);
        if (list == null) {
            return new ArrayList<>();
        }

        return list.stream()
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    private int getCount(Map<Status, List<Map.Entry<String, Status>>> groupBy, Status status) {
        final List<Map.Entry<String, Status>> value = groupBy.get(status);

        if (value == null) {
            return 0;
        }

        return value.size();
    }

}

