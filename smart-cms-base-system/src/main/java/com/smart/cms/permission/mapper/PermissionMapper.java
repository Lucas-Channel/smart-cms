package com.smart.cms.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.system.permission.PermissionDTO;
import org.apache.ibatis.annotations.Param;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:14
 * @Version: 1.0
 */
public interface PermissionMapper extends BaseMapper<PermissionDTO> {
    IPage<PermissionDTO> listPermissionsByPage(Page<PermissionDTO> page, @Param("queryParams") PermissionDTO query);
}
