package com.smart.cms.item.vo;

import com.smart.cms.service.product.Item;
import com.smart.cms.service.product.ItemPriceDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/24 16:48
 * @Version: 1.0
 */
@Data
public class ItemVo extends Item implements Serializable, Cloneable{
    private List<ItemPriceDetail> priceDetails;

    @Override
    public Object clone() {
        ItemVo itemVo = null;
        try{
            itemVo = (ItemVo)super.clone();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return itemVo;
    }
}
