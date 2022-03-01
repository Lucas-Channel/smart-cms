package com.smart.cms.system.client;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 14:56
 * @Version: 1.0
 */
@Data
@TableName("oauth_client_details")// 后期统一调整
@ApiModel(value = "Datasource对象", description = "数据源配置表")
public class ClientDetail extends BaseEntityData implements Serializable {
    private static final long serialVersionUID = 2908517265351728375L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;// 存储加密后的数据

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}
