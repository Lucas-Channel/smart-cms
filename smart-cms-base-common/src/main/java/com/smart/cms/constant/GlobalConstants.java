package com.smart.cms.constant;

/**
 * 全局常量
 *
 */
public interface GlobalConstants {

    /**
     * 全局状态-是
     */
    Integer STATUS_YES = 1;

    /**
     * 超级管理员角色编码
     */
    String ROOT_ROLE_CODE = "ADMIN";

    /**
     * [ {接口路径:[角色编码]},...]
     */
    String URL_PERM_ROLES_KEY = "system:perm_roles_rule:url";

    /**
     * [{按钮权限:[角色编码]},...]
     */
    String BTN_PERM_ROLES_KEY = "system:perm_roles_rule:btn";
    /**
     * 根部门ID
     */
    Long ROOT_DEPT_ID = 0l;

    /**
     * 根菜单ID
     */
    Long ROOT_MENU_ID = 0l;

    /**
     * 系统默认密码
     */
    String DEFAULT_USER_PASSWORD = "123456";

}
