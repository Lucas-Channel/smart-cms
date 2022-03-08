package com.smart.cms.permission.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.permission.mapper.PermissionMapper;
import com.smart.cms.permission.service.IPermissionService;
import com.smart.cms.system.permission.PermissionDTO;
import com.smart.cms.utils.other.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:16
 * @Version: 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDTO> implements IPermissionService {
    @Override
    public IPage<PermissionDTO> listPermissionsByPage(PermissionDTO query, PageData pageData) {
        Page<PermissionDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<PermissionDTO> list = this.baseMapper.listPermissionsByPage(page, query);
        return list;
    }
}
