package com.smart.cms.item.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.item.vo.ItemVo;
import com.smart.cms.service.product.Item;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/24 16:34
 * @Version: 1.0
 */
public interface ItemService extends IService<Item> {
    R saveOrUpdateLocal(ItemVo itemVo);
}
