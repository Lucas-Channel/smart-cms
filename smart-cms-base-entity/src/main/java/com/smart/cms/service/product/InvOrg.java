package com.smart.cms.service.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO 仓库表
 *
 * @Author: huilai.huang
 * @Date: 2021/12/23 16:22
 * @Version: 1.0
 */
@Data
@TableName("smart_service_inv_org_b")
@ApiModel(value = "仓库表", description = "仓库表")
public class InvOrg extends BaseEntityData implements Serializable {
    private String invOrgCode;

    private String invOrgName;

    private String address;

    private String remark;

    private String enableFlag;
}
