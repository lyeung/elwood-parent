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

package org.lyeung.elwood.web.config;

import org.lyeung.elwood.data.redis.domain.BuildResult;
import org.lyeung.elwood.data.redis.repository.BuildCountRepository;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.BuildResultRepository;
import org.lyeung.elwood.data.redis.repository.HashRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.data.redis.repository.impl.BuildCountRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.BuildRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.BuildResultRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.CountRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.ProjectRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.RedisHashRepositoryImpl;
import org.lyeung.elwood.executor.command.IncrementBuildCountCommand;
import org.lyeung.elwood.executor.command.impl.IncrementBuildCountCommandImpl;
import org.lyeung.elwood.web.config.enums.KeyType;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.GetBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.impl.DeleteBuildJobCommandImpl;
import org.lyeung.elwood.web.controller.build.command.impl.GetBuildJobCommandImpl;
import org.lyeung.elwood.web.controller.build.command.impl.SaveBuildJobCommandImpl;
import org.lyeung.elwood.web.controller.buildresult.command.GetBuildResultCommand;
import org.lyeung.elwood.web.controller.buildresult.command.impl.GetBuildResultCommandImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lyeung on 15/08/2015.
 */
@Configuration
public class ElwoodRepositoryConfiguraiton {

    private JedisPoolConfig createPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        return poolConfig;
    }

    private <K, V> RedisTemplate<K, V> createRedisTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    private <K, V> RedisTemplate<K, V> redisTemplate() {
        RedisTemplate<K, V> template = createRedisTemplate();
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    private <K, V> RedisTemplate<K, V> redisCountTemplate() {
        RedisTemplate<K, V> template = createRedisTemplate();
        template.setDefaultSerializer(new GenericToStringSerializer<>(Object.class));
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final JedisConnectionFactory factory = new JedisConnectionFactory(createPoolConfig());
        factory.setHostName("localhost");
        factory.setPort(6379);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public ProjectRepository projectRepository() {
        return new ProjectRepositoryImpl(KeyType.Project.getType(),
                new RedisHashRepositoryImpl<>(redisTemplate()));
    }

    @Bean
    public BuildRepository buildRepository() {
        return new BuildRepositoryImpl(KeyType.Build.getType(),
                new RedisHashRepositoryImpl<>(redisTemplate()));
    }

    @Bean
    public BuildCountRepository buildCountRepository() {
        return new BuildCountRepositoryImpl(KeyType.BuildCount.getType(),
                new CountRepositoryImpl<>(redisCountTemplate()));
    }

    @Bean
    public BuildResultRepository buildResultRepository() {
        final HashRepository<BuildResult, String, String> repository =
                new RedisHashRepositoryImpl<>(redisTemplate());
        return new BuildResultRepositoryImpl(KeyType.BuildResult.getType(), repository);
    }

    @Bean
    public GetBuildJobCommand getJobCommand() {
        return new GetBuildJobCommandImpl(projectRepository(), buildRepository());
    }

    @Bean
    public SaveBuildJobCommand saveJobCommand() {
        return new SaveBuildJobCommandImpl(projectRepository(), buildRepository());
    }

    @Bean
    public DeleteBuildJobCommand deleteJobCommand() {
        return new DeleteBuildJobCommandImpl(projectRepository(), buildRepository());
    }

    @Bean
    public IncrementBuildCountCommand incrementBuildCountCommand() {
        return new IncrementBuildCountCommandImpl(buildCountRepository());
    }

    @Bean
    public GetBuildResultCommand buildResultCommand() {
        return new GetBuildResultCommandImpl(buildResultRepository());
    }
}
