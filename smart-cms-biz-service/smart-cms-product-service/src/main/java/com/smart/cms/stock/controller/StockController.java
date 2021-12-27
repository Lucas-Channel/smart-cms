package com.smart.cms.stock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.service.product.stock.ItemStock;
import com.smart.cms.stock.service.StockService;
import com.smart.cms.utils.other.PageData;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 17:06
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private StockService stockService;

    @GetMapping("listPageLots")
    public R<IPage<ItemStock>> listPageLots(ItemStock itemStock, PageData pageData) {
        QueryWrapper<ItemStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(itemStock.getUomCode())) {
            queryWrapper.like("uom_code", itemStock.getUomCode());
        }
        Page<ItemStock> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<ItemStock> list = stockService.page(page, queryWrapper);
        return R.ok(list);
    }

    // 出入库，发运使用公用的方法，需要封装一个公用的接口
    public R addStockQuantity() {return null;}
}
