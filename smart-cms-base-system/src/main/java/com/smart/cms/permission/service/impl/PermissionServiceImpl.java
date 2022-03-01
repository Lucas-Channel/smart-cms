package com.smart.cms.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.permission.mapper.PermissionMapper;
import com.smart.cms.permission.service.IPermissionService;
import com.smart.cms.system.permission.PermissionDTO;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:16
 * @Version: 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDTO> implements IPermissionService {
}
