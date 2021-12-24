package com.smart.cms.item.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.item.mapper.ItemMapper;
import com.smart.cms.item.service.ItemPriceDetailService;
import com.smart.cms.item.service.ItemService;
import com.smart.cms.item.vo.ItemVo;
import com.smart.cms.service.product.Item;
import com.smart.cms.utils.AuthUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/24 16:35
 * @Version: 1.0
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ItemPriceDetailService priceDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveOrUpdateLocal(ItemVo itemVo) {
        Item item = (Item)itemVo.clone();
        initData(item);
        int row = 0;
        if (null != item.getId()) {
            row = baseMapper.updateById(item);
        } else {
            row = baseMapper.insert(item);
        }
        itemVo.getPriceDetails().forEach(detail -> {
            detail.setItemId(item.getId());
        });
        boolean b1 = priceDetailService.saveOrUpdateBatch(itemVo.getPriceDetails());
        return row > 0 && b1 ? R.ok("操作成功") : R.failed("操作成功");
    }

    protected void initData(Item item) {
        if (null != item.getId()) {
            item.setCreateTime(new Date());
            item.setCreatorCode(AuthUserInfo.getLoginUserName());
        }
        item.setUpdateTime(new Date());
        item.setUpdaterCode(AuthUserInfo.getLoginUserName());
        item.setDelFlag(0);
    }
}
