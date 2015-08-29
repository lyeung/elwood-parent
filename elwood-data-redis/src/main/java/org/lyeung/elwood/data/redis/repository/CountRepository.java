package org.lyeung.elwood.data.redis.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by lyeung on 29/08/2015.
 */
public interface CountRepository<K extends Serializable> {

    Long incrementBy(K key, long delta);

    Long getCount(K key);

    Set<K> findAll();

    void delete(List<K> keys);
}
