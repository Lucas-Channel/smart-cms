package com.smart.cms.system.job;

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
 * @Date: 2021/6/25 16:02
 * @Version: 1.0
 */
@Data
@TableName("smart_system_quartz_job")
@ApiModel(value = "定时任务", description = "定时任务")
public class QuartzJobDTO extends BaseEntityData implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";

    private static final long serialVersionUID = 2944601784762634657L;

    @ApiModelProperty(value = "定时器名称")
    private String jobName;

    @ApiModelProperty(value = "Bean名称")
    private String beanName;

    @ApiModelProperty(value = "方法名称")
    private String methodName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "状态，0暂时或1启动")
    private int isPause = 0;

    @ApiModelProperty(value = "备注")
    private String description;
}
