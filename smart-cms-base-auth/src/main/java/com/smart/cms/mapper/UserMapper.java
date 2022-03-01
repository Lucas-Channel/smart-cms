package com.smart.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.user.UserBase;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 8:55
 * @Version: 1.0
 */
public interface UserMapper extends BaseMapper<UserBase> {
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
