package com.smart.cms.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.role.service.IRoleService;
import com.smart.cms.system.role.RoleBase;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


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

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "roleCode", value = "角色编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页码大小", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "分页-角色列表", notes = "传入roleBase")
    public R<IPage<RoleBase>> list(@RequestParam RoleBase roleBase, PageData pageData) {
        QueryWrapper<RoleBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(roleBase.getRoleName())) {
            queryWrapper.like("role_name", roleBase.getRoleName());
        }
        if (StringUtils.isNotBlank(roleBase.getRoleCode())) {
            queryWrapper.like("role_code", roleBase.getRoleCode());
        }
        Page<RoleBase> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<RoleBase> pages = roleService.page(page, queryWrapper);
        return R.ok(pages);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "传入menu")
    public R saveOrUpdate(@RequestBody RoleBase roleBase) {
        roleBase.setCreateTime(new Date());
        roleBase.setDelFlag(0);
       boolean row = roleService.saveOrUpdate(roleBase);
       return row ? R.ok("操作成功") : R.failed("操作成功");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
        return R.ok(roleService.removeByIds(ids));
    }

}
