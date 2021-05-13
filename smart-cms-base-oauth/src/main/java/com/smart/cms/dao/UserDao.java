package com.smart.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.user.UserBase;

public interface UserDao extends BaseMapper<UserBase> {

    /**
     * 通过用户code 查询用户信息
     *
     * @param userCode 用户code
     * @return UserPo
     * @author LiuYongTao
     * @date 2018/11/5 16:09
     */

    UserBase findUserInfoByUsercodeAndDelFlag(String userCode, int delFlag);
}