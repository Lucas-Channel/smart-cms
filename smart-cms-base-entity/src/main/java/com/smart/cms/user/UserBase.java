package com.smart.cms.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_user")
public class UserBase extends BaseEntityData implements Serializable {
    private static final long serialVersionUID = 2220088284225413963L;
    @Column
    private String username;
    @Column
    private String usercode;
    @Column
    private String password;
    @Column
    private String sex;
    @Column
    private Date birthDate;
    @Column
    private String mobile;
    @Column
    private String address;
    @Column
    private String descritpion;
    @Column
    private String iconUrl;
}
