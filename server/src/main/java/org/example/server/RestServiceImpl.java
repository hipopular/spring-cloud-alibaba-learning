package org.example.server;

import org.example.server.rest.RestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceImpl implements RestService {

    @GetMapping("/index/{s}")
    public String index(@PathVariable String s) {
        return "Hello Nacos Discovery " + s;
    }
}
