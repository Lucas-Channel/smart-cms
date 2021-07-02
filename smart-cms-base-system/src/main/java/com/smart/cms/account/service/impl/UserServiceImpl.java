package com.smart.cms.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.account.mapper.UserMapper;
import com.smart.cms.account.service.IUserService;
import com.smart.cms.user.UserBase;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:16
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBase> implements IUserService {
}
