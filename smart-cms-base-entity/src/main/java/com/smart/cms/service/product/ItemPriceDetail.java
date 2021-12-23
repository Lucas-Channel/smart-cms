package com.smart.cms.service.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO 商品价格定义基础表
 *
 * @Author: huilai.huang
 * @Date: 2021/12/23 16:03
 * @Version: 1.0
 */
@Data
@ApiModel(value = "产品价格明细表", description = "产品价格明细表")
@TableName("smart_service_item_price")
public class ItemPriceDetail extends BaseEntityData implements Serializable {

    private Long itemId;
    // 所属库
    private Long invOrgId;
    // 计价类型: 面积，块，存放于数据字典
    private Long priceTypeId;
    // 零售标准价格
    private BigDecimal unitSellingPrice;
    // vip价格
    private BigDecimal unitVipSellingPrice;
    // 产品坐标位置
    private String coordinates;
    // 计价单位
    private String uomCode;
    // 有效期
    private Date expireDateFrom;
    // 有效期
    private Date expireDateTo;
    // 单个积分
    private BigDecimal pv;

}
