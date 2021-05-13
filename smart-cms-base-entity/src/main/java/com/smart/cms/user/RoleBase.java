package com.smart.cms.user;

import com.smart.cms.base.BaseEntityData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "sys_role")
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
    private String descritpion;
}
