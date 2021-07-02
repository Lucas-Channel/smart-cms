package com.smart.cms.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/28 15:02
 * @Version: 1.0
 */
@Data
@ApiModel(value = "查询定时任务列表参数")
public class QuartzJobRequestVO {
    @ApiModelProperty(value = "定时器名称")
    private String jobName;

    @ApiModelProperty(value = "状态，0暂时或1启动,-1异常")
    private int isPause = 0;
}
