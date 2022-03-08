package com.smart.cms.system.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:07
 * @Version: 1.0
 */
@Data
@TableName("smart_system_permission")
@ApiModel(value = "权限对象", description = "权限对象")
public class PermissionDTO extends BaseEntityData implements Serializable {
    private String name;

    private Long menuId;

    private String urlPerm;

    private String btnPerm;

    // 有权限的角色编号集合
    @TableField(exist = false)
    private List<String> roles;

    @ApiModelProperty("URL权限标识-服务名称")
    @TableField(exist = false)
    private String serviceName;

    @ApiModelProperty("URL权限标识-请求标识")
    @TableField(exist = false)
    private String requestMethod;

    @ApiModelProperty("URL权限标识-请求方式")
    @TableField(exist = false)
    private String requestPath;
}
