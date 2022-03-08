package com.smart.cms.dict.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.common.PageResult;
import com.smart.cms.common.Result;
import com.smart.cms.dict.service.IDictItemService;
import com.smart.cms.dict.service.IDictService;
import com.smart.cms.system.dict.DictDTO;
import com.smart.cms.system.dict.DictItemDTO;
import com.smart.cms.system.permission.PermissionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@RequestMapping("/dict-item")
@Api(value = "字典明细控制器", tags = "字典明细控制器")
public class DictItemController {

    private IDictItemService dictItemService;
    private IDictService dictService;

    @ApiOperation(value = "分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping("/page")
    public PageResult listDictItemsWithPage(
            long current,
            long size,
            String dictCode,
            String name
    ) {
        DictDTO one = dictService.lambdaQuery().eq(DictDTO::getDictCode, dictCode).select(DictDTO::getId).one();
        Page<DictItemDTO> result = dictItemService.page(new Page<>(current, size), new LambdaQueryWrapper<DictItemDTO>()
                .like(StrUtil.isNotBlank(name), DictItemDTO::getName, StrUtil.trimToNull(name))
                .eq(one != null && one.getId() != null, DictItemDTO::getDictId, one.getId())
                .orderByDesc(DictItemDTO::getCreateTime));
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典项列表")
    @GetMapping
    public Result list(String name, String dictCode) {
        DictDTO one = dictService.lambdaQuery().eq(DictDTO::getDictCode, dictCode).select(DictDTO::getId).one();
        List<DictItemDTO> list = dictItemService.list(
                new LambdaQueryWrapper<DictItemDTO>()
                        .like(StrUtil.isNotBlank(name), DictItemDTO::getName, name)
                        .eq(one != null && one.getId() != null, DictItemDTO::getDictId, one.getId())
                        .select(DictItemDTO::getName, DictItemDTO::getValue)
                        .orderByAsc(DictItemDTO::getSort)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "字典项详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        DictItemDTO dictItem = dictItemService.getById(id);
        return Result.success(dictItem);
    }

    @ApiOperation(value = "新增字典项")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "DictItemDTO")
    @PostMapping
    public Result add(@RequestBody DictItemDTO dictItem) {
        boolean status = dictItemService.save(dictItem);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "DictItemDTO")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody DictItemDTO dictItem) {
        boolean status = dictItemService.updateById(dictItem);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典数据")
    @ApiImplicitParam(name = "ids", value = "主键ID集合，以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = dictItemService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }


    @ApiOperation(value = "选择性更新字典数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "DictItemDTO")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody DictItemDTO dictItem) {
        LambdaUpdateWrapper<DictItemDTO> updateWrapper = new LambdaUpdateWrapper<DictItemDTO>().eq(DictItemDTO::getId, id);
        updateWrapper.set(dictItem.getStatus() != null, DictItemDTO::getStatus, dictItem.getStatus());
        boolean status = dictItemService.update(updateWrapper);
        return Result.judge(status);
    }

}
