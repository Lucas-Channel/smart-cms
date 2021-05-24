package com.smart.cms.authconstant;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 14:01
 * @Version: 1.0
 */
public class RoleConstant {
    public static final String ADMINISTRATOR = "administrator";
    public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('administrator')";
    public static final String ADMIN = "admin";
    public static final String HAS_ROLE_ADMIN = "hasAnyRole('administrator', 'admin')";
    public static final String USER = "user";
    public static final String HAS_ROLE_USER = "hasRole('user')";
    public static final String TEST = "test";
    public static final String HAS_ROLE_TEST = "hasRole('test')";

    public RoleConstant() {
    }
}
