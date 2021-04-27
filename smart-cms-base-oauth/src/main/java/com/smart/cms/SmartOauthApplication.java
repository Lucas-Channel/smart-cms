package com.smart.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/4/27 15:38
 * @Version: 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SmartOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartOauthApplication.class, args);
    }
}
