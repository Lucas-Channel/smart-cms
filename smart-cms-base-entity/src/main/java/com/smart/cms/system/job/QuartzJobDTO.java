package com.smart.cms.system.job;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
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

    private static final long serialVersionUID = 2944601784762634657L;
}
