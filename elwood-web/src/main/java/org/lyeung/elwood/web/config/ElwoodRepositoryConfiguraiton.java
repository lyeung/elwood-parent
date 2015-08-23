package org.lyeung.elwood.web.config;

import org.lyeung.elwood.data.redis.domain.Build;
import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.BuildRepository;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.data.redis.repository.impl.BuildRepositoryImpl;
import org.lyeung.elwood.data.redis.repository.impl.ProjectRepositoryImpl;
import org.lyeung.elwood.web.controller.build.command.DeleteBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.GetBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.SaveBuildJobCommand;
import org.lyeung.elwood.web.controller.build.command.impl.DeleteBuildJobCommandImpl;
import org.lyeung.elwood.web.controller.build.command.impl.GetBuildJobCommandImpl;
import org.lyeung.elwood.web.controller.build.command.impl.SaveBuildJobCommandImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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

    private <K, V> RedisTemplate<K, V> redisTemplate(Class<V> clazz) {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(clazz));
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
        return new ProjectRepositoryImpl("project", redisTemplate(Project.class));
    }

    @Bean
    public BuildRepository buildRepository() {
        return new BuildRepositoryImpl("build", redisTemplate(Build.class));
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
}
