package com.smart.cms.lot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.lot.service.LotService;
import com.smart.cms.service.product.lot.LotNumber;
import com.smart.cms.utils.CommonInitData;
import com.smart.cms.utils.other.PageData;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 16:37
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/lot")
public class LotController {
    private LotService lotService;

    @GetMapping("listPageLots")
    public R<IPage<LotNumber>> listPageLots(LotNumber lotNumber, PageData pageData) {
        QueryWrapper<LotNumber> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(lotNumber.getLotNumber())) {
            queryWrapper.like("lot_number", lotNumber.getLotNumber());
        }
        if (StringUtils.isNotBlank(lotNumber.getEnabledFlag())) {
            queryWrapper.like("enabled_flag", lotNumber.getEnabledFlag());
        }
        if (StringUtils.isNotBlank(lotNumber.getExpireDate().toString())) {
            queryWrapper.ge("expire_date", lotNumber.getExpireDate());
        }
        Page<LotNumber> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<LotNumber> list = lotService.page(page, queryWrapper);
        return R.ok(list);
    }

    @GetMapping("/listByItemId")
    public R<List<LotNumber>> listByItemId(Long itemId) {
       return R.ok(lotService.lambdaQuery().eq(LotNumber::getItemId, itemId).list());
    }

    @PostMapping("/saveOrUpdateLocal")
    public R saveOrUpdateLocal(@RequestBody LotNumber lotNumber) {
        CommonInitData.initData(lotNumber);
        boolean b = lotService.saveOrUpdate(lotNumber);
        return b ? R.ok("新增成功") : R.failed("新增失败");
    }

}
