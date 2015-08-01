package org.lyeung.elwood.web.config;

import org.lyeung.elwood.data.redis.domain.Project;
import org.lyeung.elwood.data.redis.repository.ProjectRepository;
import org.lyeung.elwood.data.redis.repository.impl.ProjectRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lyeung on 12/07/2015.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.lyeung.elwood.web.controller")
public class ElwoodConfiguration {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final JedisConnectionFactory factory = new JedisConnectionFactory(createPoolConfig());
        factory.setHostName("localhost");
        factory.setPort(6370);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public ProjectRepository projectRepository() {
       return new ProjectRepositoryImpl("project", redisTemplate(Project.class));
    }

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
}
