package org.example.server;

import javax.annotation.Resource;
import org.example.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huen on 2021/11/23 17:03
 */
//@Service
@RestController
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @GetMapping("/get/name/{tenantId}/{cinemaUid}/{id}")
    public Object getName(@PathVariable("tenantId")String tenantId,
                          @PathVariable("cinemaUid") String cinemaUid,
                          @PathVariable("id") long id){
        return userMapper.query(tenantId,cinemaUid,id);
    }
}
