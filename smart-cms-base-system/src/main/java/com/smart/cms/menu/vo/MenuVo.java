package com.smart.cms.menu.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 11:20
 * @Version: 1.0
 */
@Data
@ApiModel(value = "MenuVo对象", description = "MenuVo对象")
public class MenuVo {
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "菜单编号")
    private String menuCode;
    @ApiModelProperty(value = "菜单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
}
