package com.smart.cms.system.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
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
}
