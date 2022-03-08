package com.smart.cms.system.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/8 16:43
 * @Version: 1.0
 */
@Data
@TableName("smart_system_dict_item")
@ApiModel(value = "字典明细对象", description = "字典明细对象")
public class DictItemDTO extends BaseEntityData implements Serializable {
    private String name;

    private String value;

    private Long dictId;

    private Integer sort;

    private Integer status;

    private Integer defaulted;

    private String remark;
}
