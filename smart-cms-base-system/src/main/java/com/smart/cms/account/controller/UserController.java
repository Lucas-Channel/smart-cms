package com.smart.cms.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.account.service.IUserService;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.user.UserBase;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

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
@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)
public class UserController {
    private IUserService userService;

    @GetMapping("/listUsersPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "usercode", value = "编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页码大小", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "分页-列表", notes = "传入对象")
    public R<IPage<UserBase>> listUsersPage(@RequestParam UserBase userBase, PageData pageData) {
        QueryWrapper<UserBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(userBase.getUsername())) {
            queryWrapper.like("username", userBase.getUsername());
        }
        if (StringUtils.isNotBlank(userBase.getUsercode())) {
            queryWrapper.like("usercode", userBase.getUsercode());
        }
        Page<UserBase> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<UserBase> pages = userService.page(page, queryWrapper);
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "传入参数")
    public R saveOrUpdate(@RequestBody UserBase userBase) {
        userBase.setCreateTime(new Date());
        userBase.setDelFlag(0);
        boolean row = userService.saveOrUpdate(userBase);
        return row ? R.ok("操作成功") : R.failed("操作成功");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
        return R.ok(userService.removeByIds(ids));
    }
}
