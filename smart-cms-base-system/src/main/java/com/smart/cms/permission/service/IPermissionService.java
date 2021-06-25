package com.smart.cms.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.system.permission.PermissionDTO;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:52
 * @Version: 1.0
 */
public interface IPermissionService extends IService<PermissionDTO> {
    List<Map<String, Object>> listRoleAllotedPermissionByRoleId(Long roleId);
}
