package org.example.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.example.server.elasticsearch.ElasticsearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubbboController {

    @Reference
    ElasticsearchService elasticsearchService;

    @GetMapping("/test/{s}")
    public Object dubboTest(@PathVariable("s") String s) throws Exception {
        return elasticsearchService.existIndex(s);
    }

}
