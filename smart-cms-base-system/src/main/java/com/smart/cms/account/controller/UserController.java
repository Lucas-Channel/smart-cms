package com.smart.cms.account.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.account.service.IUserService;
import com.smart.cms.account.vo.UserQuery;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.common.PageResult;
import com.smart.cms.common.Result;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.user.AuthUser;
import com.smart.cms.user.UserBase;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:14
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@ApiIgnore
@Api(value = "用户管理", tags = "接口")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)
public class UserController {

    private IUserService userService;

    private IRolePermissionService rolePermissionService;

    @ApiOperation(value = "用户分页列表")
    @GetMapping("/page")
    public PageResult<UserBase> listUsersWithPage(
            UserQuery queryParams, PageData pageData
    ) {
        IPage<UserBase> result = userService.listUsersByPage(queryParams, pageData);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取用户表单详情")
    @GetMapping("/{userId}/form_detail")
    public Result<UserBase> getUserDetail(
            @ApiParam(value = "用户ID", example = "1") @PathVariable Long userId
    ) {
        UserBase userDetail = userService.lambdaQuery().eq(UserBase::getId, userId).one();
        return Result.success(userDetail);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public Result addUser(@RequestBody UserBase user) {
        boolean result = userService.saveUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/{userId}")
    public Result updateUser(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody UserBase user
    ) {
        user.setId(userId);
        boolean result = userService.updateUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/updateStatus")
    public Result updateUser(
            @RequestBody UserBase user
    ) {
        boolean result = userService.updateById(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{ids}")
    public Result deleteUsers(
            @ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = userService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }

    @ApiOperation(value = "获取当前登陆的用户信息")
    @GetMapping("/getCurrentUser")
    public Result<UserBase> getCurrentUser() {
        UserBase loginUserVO = new UserBase();
        // 用户基本信息
        Long userId = AuthUser.getUserId();
        UserBase user = userService.getById(userId);
        BeanUtil.copyProperties(user, loginUserVO);
        // 用户角色信息
        List<String> roles = AuthUser.getRoles();
        loginUserVO.setRoles(roles);
        // 用户按钮权限信息
        List<String> perms = rolePermissionService.listRoleAllotedPermissionByRoles(roles);
        loginUserVO.setPerms(perms);
        return Result.success(loginUserVO);
    }
}
