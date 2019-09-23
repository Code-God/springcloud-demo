package com.example.authmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableEurekaClient
@SpringBootApplication
@EnableAuthorizationServer
@MapperScan("com.example.authmanager.mapper")
public class AuthManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthManagerApplication.class, args);
    }

}
