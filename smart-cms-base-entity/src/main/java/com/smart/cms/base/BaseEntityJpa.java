package com.smart.cms.base;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
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
public class BaseEntityJpa {
    // 主键id
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // 创建人
    @Column
    private String creatorCode;
    // 更新人
    @Column
    private String updaterCode;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    @Column
    private Date createTime;
    // 更新时间
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;
    // 有效标志
    @Column
    private int delFlag;
}
