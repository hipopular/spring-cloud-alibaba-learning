package org.example.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.example.domain.Test;
import org.example.server.redisson.RedissonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huen on 2021/10/21 15:54
 */
@RestController
public class RedisController {

    @Reference
    RedissonService redissonService;

    @PostMapping("/user/add")
    public String addUser(@RequestBody Test test){
        return redissonService.input(test);
    }
}
