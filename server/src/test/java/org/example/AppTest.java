package org.example;

import com.alibaba.fastjson.JSON;
import org.example.domain.User;
import org.example.server.elasticsearch.ElasticsearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class AppTest {

    @Autowired
    ElasticsearchService elasticsearchService;

    @Test
    public void createIndexTest() throws Exception {
        // 创建索引
        elasticsearchService.createIndex("popular_index");
    }

    @Test
    public void existIndexTest() throws Exception {
        // 判断索引是否存在
        boolean existIndex = elasticsearchService.existIndex("popular_index");
        System.out.println(existIndex);
    }

    @Test
    public void deleteIndexTest() throws Exception {
        // 删除索引
        elasticsearchService.deleteIndex("test_index");
    }

    @Test
    public void addDocumentTest() throws Exception {
        // 新增文档
        User user = new User();
        user.setId(1L);
        user.setAge(12);
        user.setName("popular");
        user.setDescription("popular description");
        elasticsearchService.addDocument("popular_doc_index", user.getId().toString(), JSON.toJSONString(user));
    }

}
