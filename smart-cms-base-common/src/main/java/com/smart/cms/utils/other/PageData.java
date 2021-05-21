package com.smart.cms.utils.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 17:53
 * @Version: 1.0
 */
@Data
@ApiModel(
        description = "查询条件"
)
public class PageData {
    @ApiModelProperty("当前页")
    private Integer current;

    @ApiModelProperty("每页的数量")
    private Integer size;
}
