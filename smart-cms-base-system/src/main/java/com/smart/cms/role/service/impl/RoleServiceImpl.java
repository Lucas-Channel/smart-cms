package com.smart.cms.role.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.menu.service.IRoleMenuService;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.role.mapper.RoleMapper;
import com.smart.cms.role.service.IRoleService;
import com.smart.cms.role.service.IUserRoleService;
import com.smart.cms.system.menu.RoleMenuDTO;
import com.smart.cms.system.permission.RolePermissionDTO;
import com.smart.cms.system.role.RoleBase;
import com.smart.cms.system.role.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 14:36
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleBase> implements IRoleService {
    private IUserRoleService userRoleService;
    private IRoleMenuService roleMenuService;
    private IRolePermissionService rolePermissionService;
    @Override
    public boolean delete(List<Long> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = userRoleService.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
            Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
            roleMenuService.remove(new LambdaQueryWrapper<RoleMenuDTO>().eq(RoleMenuDTO::getRoleId, id));
            rolePermissionService.remove(new LambdaQueryWrapper<RolePermissionDTO>().eq(RolePermissionDTO::getRoleId, id));
        });
        return this.removeByIds(ids);
    }
}
