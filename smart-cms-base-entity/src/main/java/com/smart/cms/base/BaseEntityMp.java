package com.smart.cms.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * TODO 基于mp
 *
 * @Author: huilai.huang
 * @Date: 2021/4/27 15:27
 * @Version: 1.0
 */
@Data
public class BaseEntityMp {
    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 创建人
    private String creatorCode;
    // 更新人
    private String updaterCode;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date createTime;
    // 更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;
    // 有效标志
    private int delFlag;
}
