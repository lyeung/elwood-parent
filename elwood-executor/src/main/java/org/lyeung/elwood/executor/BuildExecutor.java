package org.lyeung.elwood.executor;

import java.util.concurrent.Future;

/**
 * Created by lyeung on 14/08/2015.
 */
public interface BuildExecutor {

    Future<Integer> add(String key, long count);
}
