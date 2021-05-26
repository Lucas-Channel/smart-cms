package com.smart.cms.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.dict.service.IDictService;
import com.smart.cms.system.dict.DictDTO;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class DictController {

    private IDictService dictService;

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
    @ApiOperation(value = "分页-菜单列表", notes = "传入dictDTO")
    public R<IPage<DictDTO>> list(@RequestParam DictDTO dictDTO, PageData pageData) {
        QueryWrapper<DictDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(dictDTO.getDictName())) {
            queryWrapper.like("dict_name", dictDTO.getDictName());
        }
        if (StringUtils.isNotBlank(dictDTO.getDictType())) {
            queryWrapper.like("dict_type", dictDTO.getDictType());
        }
        Page<DictDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<DictDTO> pages = dictService.page(page, queryWrapper);
        return R.ok(pages);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "传入menu")
    public R saveOrUpdate(DictDTO dictDTO) {
        dictDTO.setCreateTime(new Date());
        dictDTO.setDelFlag(0);
       boolean row = dictService.saveOrUpdate(dictDTO);
       return row ? R.ok("操作成功") : R.failed("操作成功");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
        return R.ok(dictService.removeByIds(ids));
    }

}
