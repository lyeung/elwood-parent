/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.executor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyeung on 6/08/2015.
 */
public class BuildMapLog<K extends Serializable> {

    private final int bufferLines;

    private final Map<K, BlockingQueue<String>> map = new ConcurrentHashMap<>();

    private final Map<K, Future<Integer>> futureMap = new ConcurrentHashMap<>();

    public BuildMapLog(int bufferLines) {
        this.bufferLines = bufferLines;
    }

    public void append(K key, String content) {
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

    public Optional<List<String>> getContent(K key) {
        final BlockingQueue<String> list = map.get(key);
        if (list == null) {
            return Optional.empty();
        }

        return Optional.of(new ArrayList<>(list));
    }

    public boolean removeContent(K key) {
        return map.remove(key) != null;
    }

    public Optional<Boolean> isFutureDone(K key) {
        if (!futureMap.containsKey(key)) {
            return Optional.empty();
        }

        return Optional.of(futureMap.get(key).isDone());
    }

    public Future<Integer> addFuture(K key, Future<Integer> future) {
        return futureMap.putIfAbsent(key, future);
    }

    public boolean removeFuture(K key) {
        return futureMap.remove(key) != null;
    }

    public List<K> getFutureKeys() {
        return new ArrayList<>(futureMap.keySet());
    }

}
