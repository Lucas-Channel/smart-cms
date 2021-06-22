package com.smart.cms.config;

import com.smart.cms.security.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Lucas
 * @create 2021-05-21 10:11
 */
@Configuration
public class AppBeanConfig {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)// 保证只有一个bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
