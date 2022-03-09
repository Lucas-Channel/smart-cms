package com.smart.cms.account.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/9 17:53
 * @Version: 1.0
 */
@Data
public class UserQuery {
    @ApiModelProperty("关键字(用户名、昵称、手机号)")
    private String keywords;

    @ApiModelProperty("用户状态")
    private Integer status;

    private String mobile;
}
