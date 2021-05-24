package com.smart.cms.system.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 14:46
 * @Version: 1.0
 */
@Data
@TableName("smart_system_dict")
@ApiModel(value = "字典对象", description = "字典对象")
public class DictDTO extends BaseEntityData implements Serializable {
    private static final long serialVersionUID = 7454441585390898598L;
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    @ApiModelProperty(value = "排序")
    private String sort;


}
