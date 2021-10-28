package org.example.server.redisson;

import org.example.domain.Test;

/**
 * Created by huen on 2021/10/21 15:04
 */
public interface RedissonService {

    String input(Test test);
}
