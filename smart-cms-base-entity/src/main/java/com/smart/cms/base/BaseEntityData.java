package com.smart.cms.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/4/27 15:27
 * @Version: 1.0
 */
@Data
@ApiModel(value = "基础类", description = "基础类实体")
public class BaseEntityData {
    // 主键id,使用雪花算法自动生成id
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)// 使用雪花算法生成的id精度超出17位，前端浏览器会把17位之后的数字用0自动替换，故转string返回给前端
    @ApiModelProperty(value = "主键id")
    private Long id;
    // 创建人
    @ApiModelProperty(value = "创建人")
    private String creatorCode;
    // 更新人
    @ApiModelProperty(value = "更新人")
    private String updaterCode;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    // 更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    // 有效标志
    @ApiModelProperty(value = "有效标志")
    private int delFlag;
}