package com.smart.cms;

import com.smart.cms.security.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/4/27 14:13
 * @Version: 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
