package org.lyeung.elwood.data.redis.repository.impl;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by lyeung on 29/07/2015.
 */
public class ProjectRepositoryImpl extends AbstractRepository<Project, String> implements ProjectRepository {

    public ProjectRepositoryImpl(String domainKey, RedisTemplate<String, Project> template) {
        super(domainKey, template);
    }

    @Override
    public Project getOne(String key) {
        return (Project) getTemplate().opsForHash().get(getDomainKey(), key);
    }

    @Override
    public void save(Project project) {
        getTemplate().opsForHash().put(getDomainKey(), project.getKey(), project);
    }

    @Override
    public void delete(List<String> keys) {
        getTemplate().opsForHash().delete(getDomainKey(), keys.toArray(new String[keys.size()]));
    }
}
