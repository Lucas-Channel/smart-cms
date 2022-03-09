package com.smart.cms.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.account.vo.UserQuery;
import com.smart.cms.user.UserBase;
import com.smart.cms.utils.other.PageData;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:15
 * @Version: 1.0
 */
public interface IUserService extends IService<UserBase> {
    IPage<UserBase> listUsersByPage(UserQuery queryParams, PageData pageData);

    boolean saveUser(UserBase user);

    boolean updateUser(UserBase user);
}
