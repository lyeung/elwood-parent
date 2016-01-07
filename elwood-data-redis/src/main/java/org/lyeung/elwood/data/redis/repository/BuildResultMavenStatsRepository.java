package org.lyeung.elwood.data.redis.repository;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;

import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 25/12/2015.
 */
public interface BuildResultMavenStatsRepository {

    void save(BuildResultMavenStats value);

    void delete(List<BuildResultKey> objectKeys);

    Optional<BuildResultMavenStats> getOne(BuildResultKey objectKey);
}
