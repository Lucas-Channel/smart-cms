package com.smart.cms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@ApiModel
@Data
@Builder
public class Oauth2Token {

    @ApiModelProperty("访问令牌")
    private String accessToken ;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("有效时间(秒)")
    private int expiresIn;

    @ApiModelProperty("token类型")
    private String tokenType;

    @ApiModelProperty("scope")
    private Set<String> scope;
}
