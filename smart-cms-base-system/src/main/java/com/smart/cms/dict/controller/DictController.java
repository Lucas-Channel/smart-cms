package com.smart.cms.dict.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.common.PageResult;
import com.smart.cms.common.Result;
import com.smart.cms.dict.service.IDictItemService;
import com.smart.cms.dict.service.IDictService;
import com.smart.cms.system.dict.DictDTO;
import com.smart.cms.system.dict.DictItemDTO;
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
@RequestMapping("/dict")
@Api(value = "字典控制器", tags = "字典控制器")
public class DictController {

    private IDictService dictService;
    private IDictItemService dictItemService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page")
    public PageResult<DictDTO> list(Long current, Long size, String name) {
        Page<DictDTO> result = dictService.page(new Page<>(current, size), new LambdaQueryWrapper<DictDTO>()
                .like(StrUtil.isNotBlank(name), DictDTO::getDictName, StrUtil.trimToNull(name))
                .orderByDesc(DictDTO::getCreateTime));
        return PageResult.success(result);
    }

    @ApiOperation(value = "字典列表")
    @GetMapping
    public Result list() {
        List<DictDTO> list = dictService.list(new LambdaQueryWrapper<DictDTO>()
                .orderByDesc(DictDTO::getCreateTime));
        return Result.success(list);
    }


    @ApiOperation(value = "字典详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        DictDTO dict = dictService.getById(id);
        return Result.success(dict);
    }

    @ApiOperation(value = "新增字典")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "DictItemDTO")
    @PostMapping
    public Result add(@RequestBody DictDTO dict) {
        boolean status = dictService.save(dict);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "DictItemDTO")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody DictDTO dict) {

        boolean status = dictService.updateById(dict);
        if (status) {
            DictDTO dbDict = dictService.getById(id);
            // 字典code更新，同步更新字典项code
            if (!StrUtil.equals(dbDict.getDictCode(), dict.getDictCode())) {
                dictItemService.update(new LambdaUpdateWrapper<DictItemDTO>().eq(DictItemDTO::getDictId, dbDict.getId())
                        .set(DictItemDTO::getDictId, dict.getId()));
            }
        }
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        if (CollectionUtil.isNotEmpty(idList)) {
            int count = dictItemService.count(new LambdaQueryWrapper<DictItemDTO>().in(DictItemDTO::getDictId, idList));
            Assert.isTrue(count == 0, "删除字典失败，请先删除关联字典数据");
        }
        boolean status = dictService.removeByIds(idList);
        return Result.judge(status);
    }

}
