package com.smart.cms.security.properties;

import lombok.Data;

/**
 * 前后端分离，token必要的参数
 *
 * @author Lucas
 * @create 2021-04-27 11:25
 */
@Data
public class ClientProperties {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String accessTokenUri;
    private String scope;
}
