package com.smart.cms.permission.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 12:01
 * @Version: 1.0
 */
@Data
@ApiModel(value = "已分配菜单", description = "已分配菜单")
public class AllotedPermissionResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private String menuName;

    private int isAllot;
}
