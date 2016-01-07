package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.BuildResultKey;
import org.lyeung.elwood.data.redis.domain.BuildResultMavenStats;
import org.lyeung.elwood.data.redis.repository.BuildResultMavenStatsRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by lyeung on 25/12/2015.
 */
public class BuildResultMavenStatsRepositoryImpl implements BuildResultMavenStatsRepository {

    private final HashRepository<BuildResultMavenStats, String, String> repository;

    private final String keyType;

    public BuildResultMavenStatsRepositoryImpl(
            String keyType, HashRepository<BuildResultMavenStats, String, String> repository) {
        this.keyType = keyType;
        this.repository = repository;
    }

    @Override
    public void save(BuildResultMavenStats value) {
        repository.save(keyType + value.getKey().toStringValue(),
                value.getKey().toStringValue(), value);
    }

    @Override
    public void delete(List<BuildResultKey> objectKeys) {
        objectKeys.forEach(k ->
                repository.delete(keyType + k.toStringValue(),
                        Collections.singletonList(k.toStringValue())));
    }

    @Override
    public Optional<BuildResultMavenStats> getOne(BuildResultKey buildResultKey) {
        return repository.getOne(keyType + buildResultKey.toStringValue(),
                buildResultKey.toStringValue());
    }
}
