package org.example;

import static org.junit.Assert.assertTrue;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
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
}
