package com.fc.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 分布式锁redisson 用于解决集群环境的并发问题
 */
@Component
public class RedisConfig {

    private String potocol = "redis://";

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.useSingleServer().setAddress(potocol + host + ":" + port).setDatabase(0);

        return Redisson.create(config);
    }
}
