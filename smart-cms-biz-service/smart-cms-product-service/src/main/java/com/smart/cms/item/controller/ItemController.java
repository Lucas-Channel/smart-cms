package com.smart.cms.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.item.service.ItemPriceDetailService;
import com.smart.cms.item.service.ItemService;
import com.smart.cms.item.vo.ItemVo;
import com.smart.cms.service.product.Item;
import com.smart.cms.utils.AuthUserInfo;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/24 16:35
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/item")
@Api(value = "商品信息基础控制器", tags = "商品信息基础控制器")
public class ItemController {
    private ItemService itemService;

    private ItemPriceDetailService priceDetailService;

    @GetMapping("/listItemPage")
    @ApiOperation(value = "分页-列表", notes = "传入对象")
    public R<IPage<Item>> listItemPage(Item item, PageData pageData) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(item.getItemCode())) {
            queryWrapper.like("item_code", item.getItemCode());
        }
        if (StringUtils.isNotBlank(item.getItemName())) {
            queryWrapper.like("item_name", item.getItemName());
        }
        if (StringUtils.isNotBlank(item.getPublishStatus())) {
            queryWrapper.like("publish_flag", item.getPublishStatus());
        }
        if (StringUtils.isNotBlank(item.getItemType())) {
            queryWrapper.like("item_type", item.getItemType());
        }
        Page<Item> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<Item> pages = itemService.page(page, queryWrapper);
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增", notes = "传入对象")
    public R saveOrUpdate(@RequestBody ItemVo itemVo) {
        return itemService.saveOrUpdateLocal(itemVo);
    }

    @DeleteMapping("/deleteBatchByIds")
    @ApiOperation(value = "批量删除", notes = "批量删除")
    public R deleteBatchByIds(@RequestBody List<Long> ids) {
        List<Item> dels = new ArrayList<>();
        ids.forEach( i -> {
            Item a = new Item();
            a.setId(i);
            a.setDelFlag(1);
            a.setUpdateTime(new Date());
            a.setUpdaterCode(AuthUserInfo.getLoginUserName());
            dels.add(a);
        });
        boolean b = itemService.updateBatchById(dels);
        return b ? R.ok("删除成功") : R.failed("删除成功");
    }
}
