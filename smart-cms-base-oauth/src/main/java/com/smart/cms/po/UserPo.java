package com.smart.cms.po;

import com.smart.cms.base.BaseEntityJpa;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_user")
public class UserPo extends BaseEntityJpa implements Serializable {
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
