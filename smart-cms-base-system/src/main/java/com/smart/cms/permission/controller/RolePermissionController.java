package com.smart.cms.permission.controller;

import com.smart.cms.permission.service.IRolePermissionService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:54
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role-permission")
@Api(value = "角色权限控制器", tags = "角色权限控制器")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)// 必须拥有超级管理员权限才可以访问这个类的接口
public class RolePermissionController {

    private IRolePermissionService service;

}
