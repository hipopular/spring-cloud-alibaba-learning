package org.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huen on 2021/10/21 17:26
 */
@Configuration
public class RedissonConfing {

    @Bean
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();
        String addr = String.format("redis://%s:%s", properties.getHost(), properties.getPort());
        config.useSingleServer()
                .setAddress(addr)
                .setDatabase(properties.getDatabase())
                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }

}
