package com.koala.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * koala 启动类
 */
@SpringBootApplication(scanBasePackages = "com.koala.config")
public class KoalaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KoalaApplication.class);
    }
}
