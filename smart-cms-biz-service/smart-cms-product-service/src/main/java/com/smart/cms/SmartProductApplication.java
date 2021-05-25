package com.smart.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/25 17:33
 * @Version: 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.smart.cms.**")
public class SmartProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartProductApplication.class, args);
    }
}
