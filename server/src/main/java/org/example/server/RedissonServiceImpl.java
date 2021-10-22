package org.example.server;

import org.apache.dubbo.config.annotation.Service;
import org.example.domain.User;
import org.example.server.redisson.RedissonService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huen on 2021/10/21 15:05
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String input(User user){
        RBucket<User> bucket = redissonClient.getBucket("user:".concat(user.getId().toString()));
        bucket.set(user);
        return "success";
    }
}
