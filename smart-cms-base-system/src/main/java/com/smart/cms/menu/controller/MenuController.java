package com.smart.cms.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.MenuVo;
import com.smart.cms.system.menu.MenuDTO;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


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
@Api(value = "代码生成", tags = "代码生成")
//@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class MenuController {
    private IMenuService menuService;

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuName", value = "菜单名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "menuCode", value = "菜单编号", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "分页", notes = "传入menuVo")
    public R<IPage<MenuDTO>> list(@ApiIgnore @RequestParam MenuVo menuVo, PageData pageData) {
        QueryWrapper<MenuDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
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
}
