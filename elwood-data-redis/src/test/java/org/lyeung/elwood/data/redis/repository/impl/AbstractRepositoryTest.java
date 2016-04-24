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

package org.lyeung.elwood.data.redis.repository.impl;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lyeung on 1/08/2015.
 */
public abstract class AbstractRepositoryTest {

    private static final int REDIS_DEFAULT_PORT = 6379;

    private static final String REDIS_HOSTNAME = "localhost";

    <K, V> RedisTemplate<K, V> redisTemplate() {
        final RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    <K, V> RedisTemplate<K, V> redisCountTemplate() {
        final RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new GenericToStringSerializer<>(Object.class));
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
        factory.setHostName(redisHostname());
        factory.setPort(redisPort());
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }

    private String redisHostname() {
        String hostname = System.getenv("REDIS_HOSTNAME");
        if (hostname == null) {
            return REDIS_HOSTNAME;
        }

        return hostname;
    }

    private int redisPort() {
        String redisPort = System.getenv("REDIS_PORT");
        if (redisPort == null) {
            return REDIS_DEFAULT_PORT;
        }

        try {
            return Integer.parseInt(redisPort);
        } catch (NumberFormatException e) {
            return REDIS_DEFAULT_PORT;
        }
    }
}
