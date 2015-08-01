package org.lyeung.elwood.data.redis.repository.impl;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lyeung on 1/08/2015.
 */
public abstract class AbstractRepositoryTest {

    <K, V> RedisTemplate<K, V> redisTemplate(Class<V> clazz) {
        final RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(clazz));
        template.afterPropertiesSet();
        return template;
    }

    private JedisConnectionFactory jedisConnectionFactory() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        final JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
        factory.setHostName("localhost");
        factory.setPort(6379);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }
}
