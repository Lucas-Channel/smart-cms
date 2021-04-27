package com.smart.cms.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * security 的配置信息
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private TokenProperties token = new TokenProperties();
    // 网关白名单配置
    private List<String> whiteList = new ArrayList<>();
}
