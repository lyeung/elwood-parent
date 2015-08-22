package org.lyeung.elwood.web.config;

import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.executor.BuildExecutor;
import org.lyeung.elwood.executor.BuildMapLog;
import org.lyeung.elwood.executor.command.BuildArticleCommandFactory;
import org.lyeung.elwood.executor.command.impl.BuildArticleCommandFactoryImpl;
import org.lyeung.elwood.executor.impl.BuildExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyeung on 15/08/2015.
 */
@Configuration
public class ElwoodExecutorConfiguration {

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 6;
    private static final int KEEP_ALIVE_IN_SECONDS = 60;
    private static final int QUEUE_CAPACITY = 10;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    BuildRepository buildRepository;

    @Autowired
    private BuildMapLog buildMapLog;

    @Bean
    public BuildExecutor buildExecutor() {
        return new BuildExecutorImpl(buildArticleCommandFactory(),
                new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_IN_SECONDS,
                        TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                        new ThreadPoolExecutor.AbortPolicy()));
    }

    private BuildArticleCommandFactory buildArticleCommandFactory() {
        return new BuildArticleCommandFactoryImpl(projectRepository, buildRepository, buildMapLog);
    }
}
