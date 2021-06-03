package com.smart.cms.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/3 13:53
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setDbType(DbType.MYSQL);
        return interceptor;
    }
}
