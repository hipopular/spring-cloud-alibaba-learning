package org.example.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
class NacosController{

    @Value("${spring.profiles.active}")
    String profile;

    public void nacosTest() {
        System.out.printf("Initial username=%s%n", profile);
    }
}
