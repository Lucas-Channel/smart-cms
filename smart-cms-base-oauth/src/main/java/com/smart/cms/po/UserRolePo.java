package com.smart.cms.po;


import com.smart.cms.utils.basepo.BasePo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class UserRolePo extends BasePo implements Serializable {
    private static final long serialVersionUID = 2220088284225413966L;
    // 用户id
    @Column
    private Long userId;
    // 角色id
    @Column
    private Long roleId;

}
