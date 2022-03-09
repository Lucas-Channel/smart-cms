package com.smart.cms.role.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/9 11:20
 * @Version: 1.0
 */
@Accessors(chain = true)
@Data
public class RolePermissionVo {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID集合
     */
    private List<Long> permIds;
}
