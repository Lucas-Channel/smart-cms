package com.smart.cms.permission.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.permission.service.IPermissionService;
import com.smart.cms.permission.vo.AllotPermissionRequest;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:54
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/permission")
@Api(value = "角色权限控制器", tags = "角色权限控制器")
@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)// 必须拥有超级管理员权限才可以访问这个类的接口
public class PermissionController {

    @Autowired
    private IPermissionService service;

    @PostMapping("/allotPermission")
    @ApiOperation(value = "分配菜单权限", notes = "分配菜单权限")
    public R allotPermission(@RequestBody AllotPermissionRequest allotPermissionRequest) {
        allotPermissionRequest.getPermissionDTOList().forEach(it -> {
            it.setRoleId(allotPermissionRequest.getRoleId());
            it.setCreateTime(new Date());
            it.setDelFlag(0);
        });
        boolean row = service.saveOrUpdateBatch(allotPermissionRequest.getPermissionDTOList());
        return row ? R.ok("分配成功") : R.failed("分配失败");
    }

    @GetMapping("/listRoleAllotedPermissionByRoleId")
    @ApiOperation(value = "查询角色已分配权限", notes = "查询角色已分配权限")
    public R<List<Map<String, Object>>> listRoleAllotedPermissionByRoleId(@NotNull Long roleId) {
        return R.ok(service.listRoleAllotedPermissionByRoleId(roleId));
    }
}
