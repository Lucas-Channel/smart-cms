package com.smart.cms.system.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:46
 * @Version: 1.0
 */
@Data
@TableName("smart_system_role_permission")
@ApiModel(value = "角色权限对象", description = "角色权限对象")
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO extends BaseEntityData implements Serializable {

    private static final long serialVersionUID = 5196985930231945814L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "权限id")
    private Long permissionId;


}
