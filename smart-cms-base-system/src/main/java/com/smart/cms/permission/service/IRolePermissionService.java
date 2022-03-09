package com.smart.cms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.role.vo.RolePermissionVo;
import com.smart.cms.system.permission.RolePermissionDTO;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:52
 * @Version: 1.0
 */
public interface IRolePermissionService extends IService<RolePermissionDTO> {
    List<Map<String, Object>> listRoleAllotedPermissionByRoleId(Long roleId);

    boolean refreshPermRolesRules();

    List<String> listRoleAllotedPermissionByRoles(List<String> roles);

    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    List<Long> listPermIds(Long menuId, Long roleId);

    boolean saveRolePerms(RolePermissionVo rolePermissionVo);
}
