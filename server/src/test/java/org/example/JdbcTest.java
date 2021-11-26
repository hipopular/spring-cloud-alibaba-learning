package org.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huen on 2021/10/25 15:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class JdbcTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
    }
}
