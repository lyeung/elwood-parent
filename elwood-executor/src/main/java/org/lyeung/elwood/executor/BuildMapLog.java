package org.lyeung.elwood.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyeung on 6/08/2015.
 */
public class BuildMapLog {

    private final int bufferLines;

    private final Map<String, BlockingQueue<String>> map = new ConcurrentHashMap<>();

    public BuildMapLog(int bufferLines) {
        this.bufferLines = bufferLines;
    }

    public void append(String key, String content) {
        map.putIfAbsent(key, new LinkedBlockingQueue<>(bufferLines));

        final BlockingQueue<String> queue = map.get(key);
        reachedCapacity(queue);
        addTail(content, queue);
    }

    private void addTail(String content, BlockingQueue<String> queue) {
        try {
            queue.offer(content, 100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void reachedCapacity(BlockingQueue<String> queue) {
        if (queue.remainingCapacity() == 0) {
            try {
                queue.poll(100L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Optional<List<String>> get(String key) {
        final BlockingQueue<String> list = map.get(key);
        if (list == null) {
            return Optional.empty();
        }

        return Optional.of(new ArrayList<>(list));
    }

}
