package com.smart.cms.system.role;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.base.BaseEntityData;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * user与role关联关系，多对多
 */
@Data
@TableName("smart_user_role")
@Accessors(chain = true)
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
