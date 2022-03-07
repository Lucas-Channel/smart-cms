package com.smart.cms.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.MenuVo;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.system.menu.MenuDTO;
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
@RequestMapping("/menu")
@Api(value = "菜单控制器", tags = "菜单控制器")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)// 必须拥有超级管理员权限才可以访问这个类的接口
public class MenuController {

    private IMenuService menuService;

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuName", value = "菜单名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "menuCode", value = "菜单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页码大小", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "分页-菜单列表", notes = "传入menuVo")
    public R<IPage<MenuDTO>> list(@RequestParam MenuVo menuVo, PageData pageData) {
        QueryWrapper<MenuDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        queryWrapper.eq("parent_id", 0);// 获取一级目录列表
        if (StringUtils.isNotBlank(menuVo.getMenuName())) {
            queryWrapper.like("name", menuVo.getMenuName());
        }
        if (StringUtils.isNotBlank(menuVo.getMenuCode())) {
            queryWrapper.like("code", menuVo.getMenuCode());
        }
        Page<MenuDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<MenuDTO> pages = menuService.page(page, queryWrapper);
        return R.ok(pages);
    }

    /**
     * 获取母菜单下子菜单列表
     */
    @GetMapping("/menuList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级菜单id", paramType = "query", dataType = "Long")
    })
    @ApiOperation(value = "菜单列表", notes = "传入母菜单id")
    public R<List<MenuDTO>> menuList(@RequestParam MenuVo menu) {
        List<MenuDTO> list = menuService.lambdaQuery()
                .eq(MenuDTO::getParentId, menu.getParentId())
                .eq(MenuDTO::getDelFlag, 0)
                .list();
        return R.ok(list);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "传入menu")
    public R saveOrUpdate(@RequestBody MenuDTO menuDTO) {
        menuDTO.setCreateTime(new Date());
        menuDTO.setDelFlag(0);
       boolean row = menuService.saveOrUpdate(menuDTO);
       return row ? R.ok("操作成功") : R.failed("操作成功");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
        return R.ok(menuService.removeByIds(ids));
    }

    @GetMapping("/listMenus")
    public R<List<RouteVO>> listMenus() {
        List<RouteVO> list = menuService.listRoute();
        return R.ok(list);
    }
}
