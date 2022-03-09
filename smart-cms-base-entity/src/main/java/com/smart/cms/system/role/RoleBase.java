package com.smart.cms.system.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("smart_role")
public class RoleBase extends BaseEntityData implements Serializable {
    private static final long serialVersionUID = 2220088284222413966L;
    /**
     * 角色名称
     */
    @Column
    private String roleName;
    /**
     * 角色CODE
     */
    @Column
    private String roleCode;
    /**
     * 角色描述
     */
    @Column
    private String description;

    @TableField(exist = false)
    private List<Long> menuIds;

    @TableField(exist = false)
    private List<Long> permissionIds;
}
