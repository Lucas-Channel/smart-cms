package com.smart.cms.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结构体
 */
@Data
public class PageResult<T> implements Serializable {

    private String code;

    private List<T> data;

    private long total;

    private String msg;

    public static <T> PageResult<T> success(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(page.getRecords());
        result.setTotal(page.getTotal());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }
}
