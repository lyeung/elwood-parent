package org.lyeung.elwood.data.redis.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyeung on 1/08/2015.
 */
public interface CrudRepository<T, K extends Serializable> {

    T getOne(K key);

    void save(T domain);

    void delete(List<K> keys);

    List<T> findAll();
}
