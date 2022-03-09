package com.smart.cms.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.role.mapper.UserRoleMapper;
import com.smart.cms.role.service.IUserRoleService;
import com.smart.cms.system.role.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 14:36
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
