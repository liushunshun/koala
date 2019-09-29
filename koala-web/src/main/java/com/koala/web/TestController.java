package com.koala.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@EnableAutoConfiguration
@RestController
public class TestController {

    @GetMapping("/sayHello")
    public String sayHello(String name){
        return "Hello "+name;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestController.class, args);
    }
}
