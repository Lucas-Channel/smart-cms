package com.smart.cms.menu.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.common.Result;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.IdLabelVO;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.menu.vo.ValueLabelVO;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.system.menu.MenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@RequestMapping("/menu")
@Api(value = "菜单控制器", tags = "菜单控制器")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)// 必须拥有超级管理员权限才可以访问这个类的接口
public class MenuController {

    private IMenuService menuService;
    private IRolePermissionService permissionService;

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result addMenu(@RequestBody MenuDTO menu) {
        boolean result = menuService.saveMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result updateMenu(
            @ApiParam("菜单ID") @PathVariable Long id,
            @RequestBody MenuDTO menu
    ) {
        boolean result = menuService.updateMenu(menu);
        return Result.judge(result);
    }
    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除", notes = "传入ids")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result remove(@ApiParam(value = "主键集合", required = true) @PathVariable("ids") String ids) {
        boolean result = menuService.removeByIds(Arrays.asList(ids.split(",")));
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @GetMapping("/listMenus")
    public R<List<RouteVO>> listMenus() {
        List<RouteVO> list = menuService.listRoute();
        return R.ok(list);
    }

    @ApiOperation(value = "菜单表格（Table）层级列表")
    @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String")
    @GetMapping("/table")
    public Result getTableList(String name) {
        List<MenuDTO> menuList = menuService.listTable(name);
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单下拉（Select）层级列表")
    @GetMapping("/select")
    public Result getSelectList() {
        List<ValueLabelVO> menuList = menuService.listSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单树形（TreeSelect）层级列表")
    @GetMapping("/tree_select")
    public Result getTreeSelectList() {
        List<IdLabelVO> menuList = menuService.listTreeSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单详情")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        MenuDTO menu = menuService.getById(id);
        return Result.success(menu);
    }
}
