package com.smart.cms.system.code;

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
 * @Date: 2021/5/21 17:38
 * @Version: 1.0
 */
@Data
@TableName("smart_system_datasource")
@ApiModel(value = "Datasource对象", description = "数据源配置表")
public class Datasource extends BaseEntityData implements Serializable {

    private static final long serialVersionUID = -2120354784925078440L;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 驱动类
     */
    @ApiModelProperty(value = "驱动类")
    private String driverClass;
    /**
     * 连接地址
     */
    @ApiModelProperty(value = "连接地址")
    private String url;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
