package com.smart.cms.service.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TODO 产品基础信息表
 *
 * @Author: huilai.huang
 * @Date: 2021/12/23 15:39
 * @Version: 1.0
 */
@Data
@TableName("smart_service_item_b")
@ApiModel(value = "产品对象", description = "产品对象")
public class Item extends BaseEntityData implements Serializable, Cloneable{
    private String itemName;

    private String itemCode;

    private String qrCode;

    private String remark;

    private String itemType;

    private BigDecimal itemArea;

    private BigDecimal itemWeight;

    private BigDecimal itemLong;

    private BigDecimal itemWidth;

    private BigDecimal itemThick;

    private String publishStatus;

    @Override
    public Object clone() {
        Item item = null;
        try{
            item = (Item)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return item;
    }
}
