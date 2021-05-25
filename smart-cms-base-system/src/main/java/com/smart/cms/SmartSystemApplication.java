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
 * @Date: 2021/4/28 17:51
 * @Version: 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.smart.cms.**")
public class SmartSystemApplication {
    // 如果项目会用到城市，建议启动的时候就加载数据到缓存
    public static void main(String[] args) {
        SpringApplication.run(SmartSystemApplication.class, args);
    }
}
