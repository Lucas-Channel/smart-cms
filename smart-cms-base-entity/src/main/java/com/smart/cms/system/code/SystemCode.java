package com.smart.cms.system.code;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO 代码生成数据存储表
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 17:14
 * @Version: 1.0
 */
@Data
@TableName("smart_system_code")
@ApiModel(value = "Code对象", description = "Code对象")
public class SystemCode extends BaseEntityData  implements Serializable {

    private static final long serialVersionUID = -5188971075742197480L;

    /**
     * 数据源主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "数据源主键")
    private Long datasourceId;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String codeName;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

    /**
     * 实体名
     */
    @ApiModelProperty(value = "表前缀")
    private String tablePrefix;

    /**
     * 主键名
     */
    @ApiModelProperty(value = "主键名")
    private String pkName;

    /**
     * 基础业务模式
     */
    @ApiModelProperty(value = "基础业务模式")
    private Integer baseMode;

    /**
     * 包装器模式
     */
    @ApiModelProperty(value = "包装器模式")
    private Integer wrapMode;

    /**
     * 后端包名
     */
    @ApiModelProperty(value = "后端包名")
    private String packageName;

    /**
     * 后端路径
     */
    @ApiModelProperty(value = "后端路径")
    private String apiPath;

    /**
     * 前端路径
     */
    @ApiModelProperty(value = "前端路径")
    private String webPath;
}
