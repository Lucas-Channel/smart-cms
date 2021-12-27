package com.smart.cms.service.product.lot;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.exception.DataException;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 16:22
 * @Version: 1.0
 */
@Data
@TableName("smart_service_item_lot")
@ApiModel(value = "产品批次", description = "产品批次")
public class LotNumber extends BaseEntityData implements Serializable {
    private Long itemId;
    // 所属库
    private Long invOrgId;

    private String lotNumber;

    private Date expireDate;

    private String enabledFlag;
}
