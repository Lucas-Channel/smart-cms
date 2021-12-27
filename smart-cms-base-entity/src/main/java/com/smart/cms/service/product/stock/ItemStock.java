package com.smart.cms.service.product.stock;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 16:17
 * @Version: 1.0
 */
@Data
@TableName("smart_service_item_stock")
@ApiModel(value = "产品文件库存对象", description = "产品文件库存对象")
public class ItemStock extends BaseEntityData implements Serializable {

    private Long itemId;
    // 所属库
    private Long invOrgId;

    private Long lotNumberId;

    private String uomCode;

    private String quantity;

}
