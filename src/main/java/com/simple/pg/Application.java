package com.simple.pg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Rui
 * @date 2026/1/22
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@MapperScan(basePackages = "com.simple.pg.repo")
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
