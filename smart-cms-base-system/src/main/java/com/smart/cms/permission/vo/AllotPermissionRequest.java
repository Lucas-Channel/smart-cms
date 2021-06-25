package com.smart.cms.permission.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.system.permission.PermissionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * TODO 分配角色权限的请求参数接收对象
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 11:00
 * @Version: 1.0
 */
@Data
@ApiModel(value = "permission对象", description = "permission对象")
public class AllotPermissionRequest {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源列表")
    private List<PermissionDTO> permissionDTOList;
}
