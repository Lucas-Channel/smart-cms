package com.smart.cms.user;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.base.BaseEntityData;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Data
@TableName("sys_user_role")
public class UserRole extends BaseEntityData implements Serializable {
    private static final long serialVersionUID = 2220088284225413966L;
    // 用户id
    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    // 角色id
    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}
