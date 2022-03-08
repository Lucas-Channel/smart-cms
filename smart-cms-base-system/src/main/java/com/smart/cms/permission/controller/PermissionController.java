package com.smart.cms.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smart.cms.common.PageResult;
import com.smart.cms.common.Result;
import com.smart.cms.permission.service.IPermissionService;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.system.permission.PermissionDTO;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:17
 * @Version: 1.0
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/permission")
@Api(value = "权限控制器", tags = "权限控制器")
public class PermissionController {

    private IPermissionService permissionService;
    private IRolePermissionService rolePermissionService;

    @ApiOperation(value = "列表分页")
    @GetMapping("/list")
    public PageResult<PermissionDTO> listPermissionsWithPage(
            PermissionDTO query, PageData pageData
    ) {
        IPage<PermissionDTO> result = permissionService.listPermissionsByPage(query, pageData);
        return PageResult.success(result);
    }

    @ApiOperation(value = "权限列表")
    @GetMapping
    public Result listPermissions(
            @ApiParam(value = "菜单ID") @RequestParam(required = false) Long menuId
    ) {
        List<PermissionDTO> list = permissionService.list(
                new LambdaQueryWrapper<PermissionDTO>()
                        .eq(menuId != null, PermissionDTO::getMenuId, menuId)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "权限详情")
    @GetMapping("/{permissionId}")
    public Result getPermissionDetail(
            @ApiParam("权限ID") @PathVariable Long permissionId
    ) {
        PermissionDTO permission = permissionService.getById(permissionId);
        return Result.success(permission);
    }

    @ApiOperation(value = "新增权限")
    @PostMapping
    public Result addPermission(
            @RequestBody PermissionDTO permission
    ) {
        boolean result = permissionService.save(permission);
        if (result) {
            rolePermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改权限")
    @PutMapping(value = "/{permissionId}")
    public Result updatePermission(
            @ApiParam("权限ID") @PathVariable Long permissionId,
            @RequestBody PermissionDTO permission
    ) {
        boolean result = permissionService.updateById(permission);
        if (result) {
            rolePermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/{ids}")
    public Result deletePermissions(
            @ApiParam("权限ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = permissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
