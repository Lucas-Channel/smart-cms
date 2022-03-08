package com.smart.cms.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.system.permission.PermissionDTO;
import com.smart.cms.utils.other.PageData;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:15
 * @Version: 1.0
 */
public interface IPermissionService extends IService<PermissionDTO> {
    IPage<PermissionDTO> listPermissionsByPage(PermissionDTO query, PageData pageData);
}
