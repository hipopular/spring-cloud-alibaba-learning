package org.example;

import com.alibaba.fastjson.JSON;
import org.example.domain.Test;
import org.example.server.elasticsearch.ElasticsearchService;
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

    @org.junit.Test
    public void createIndexTest() throws Exception {
        // 创建索引
        elasticsearchService.createIndex("popular_index");
    }

    @org.junit.Test
    public void existIndexTest() throws Exception {
        // 判断索引是否存在
        boolean existIndex = elasticsearchService.existIndex("popular_index");
        System.out.println(existIndex);
    }

    @org.junit.Test
    public void deleteIndexTest() throws Exception {
        // 删除索引
        elasticsearchService.deleteIndex("test_index");
    }

    @org.junit.Test
    public void addDocumentTest() throws Exception {
        // 新增文档
        Test test = new Test();
        test.setUid(1L);
        test.setAge(12);
        test.setName("popular");
        elasticsearchService.addDocument("popular_doc_index", test.getUid().toString(), JSON.toJSONString(test));
    }

}
