package com.smart.cms.role.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.common.PageResult;
import com.smart.cms.common.Result;
import com.smart.cms.constant.GlobalConstants;
import com.smart.cms.menu.service.IRoleMenuService;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.role.service.IRoleService;
import com.smart.cms.role.vo.RolePermissionVo;
import com.smart.cms.system.role.RoleBase;
import com.smart.cms.user.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.security.access.prepost.PreAuthorize;


/**
 * TODO 菜单管理
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 10:55
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色控制器", tags = "角色控制器")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)// 必须拥有超级管理员权限才可以访问这个类的接口
public class RoleController {

    private IRoleService roleService;
    private IRolePermissionService permissionService;
    private IRoleMenuService roleMenuService;

    @ApiOperation(value = "列表分页")
    @GetMapping("/page")
    public PageResult<RoleBase> getRolePageList(
            @ApiParam("页码") long current,
            @ApiParam("每页数量") long size,
            @ApiParam("角色名称") String name
    ) {
        List<String> roles = AuthUser.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        LambdaQueryWrapper<RoleBase> queryWrapper = new LambdaQueryWrapper<RoleBase>()
                .like(StrUtil.isNotBlank(name), RoleBase::getRoleName, name)
                .ne(!isRoot, RoleBase::getRoleCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(RoleBase::getCreateTime);
        Page<RoleBase> result = roleService.page(new Page<>(current, size), queryWrapper);
        return PageResult.success(result);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping
    public Result getRoleList() {
        List<String> roles = AuthUser.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        List list = roleService.list(new LambdaQueryWrapper<RoleBase>()
                .ne(!isRoot, RoleBase::getRoleCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(RoleBase::getCreateTime)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result getRoleDetail(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        RoleBase role = roleService.getById(roleId);
        return Result.success(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping
    public Result saveRole(@RequestBody RoleBase role) {
        int count = roleService.count(new LambdaQueryWrapper<RoleBase>()
                .eq(RoleBase::getRoleCode, role.getRoleCode())
                .or()
                .eq(RoleBase::getRoleName, role.getRoleName())
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = roleService.save(role);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/{id}")
    public Result updateRole(
            @ApiParam("角色ID") @PathVariable Long id,
            @RequestBody RoleBase role) {
        int count = roleService.count(new LambdaQueryWrapper<RoleBase>()
                .ne(RoleBase::getId, id)
                .and(wrapper ->
                        wrapper.eq(RoleBase::getRoleCode, role.getRoleCode())
                                .or()
                                .eq(RoleBase::getRoleName, role.getRoleName())
                ));
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = roleService.updateById(role);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{ids}")
    public Result deleteRoles(
            @ApiParam("删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = roleService.delete(Arrays.asList(ids.split(",")).stream()
                .map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "获取角色的菜单ID集合")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{roleId}/menu_ids")
    public Result listRoleMenuIds(
            @ApiParam("角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleMenuService.listMenuIds(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation(value = "获取角色的权限ID集合")
    @GetMapping("/{roleId}/permissions")
    public Result listRolePermission(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @ApiParam("菜单ID") Long menuId
    ) {
        List<Long> permissionIds = permissionService.listPermIds(menuId, roleId);
        return Result.success(permissionIds.stream().map(it -> String.valueOf(it)).collect(Collectors.toList()));
    }

    @ApiOperation(value = "修改角色菜单")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    @PutMapping(value = "/{roleId}/menus")
    public Result updateRoleMenu(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody RoleBase role
    ) {
        List<Long> menuIds = role.getMenuIds();
        boolean result = roleMenuService.update(roleId, menuIds);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }


    @ApiOperation(value = "修改角色权限")
    @PutMapping(value = "/{roleId}/permissions")
    public Result saveRolePerms(
            @ApiParam("角色ID") @PathVariable Long roleId,
            @RequestBody RolePermissionVo rolePerms) {
        rolePerms.setRoleId(roleId);
        boolean result = permissionService.saveRolePerms(rolePerms);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }
}
