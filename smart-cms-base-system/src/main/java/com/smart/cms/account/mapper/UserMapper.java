package com.smart.cms.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.account.vo.UserQuery;
import com.smart.cms.user.UserBase;
import org.apache.ibatis.annotations.Param;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:12
 * @Version: 1.0
 */
public interface UserMapper extends BaseMapper<UserBase> {
    IPage<UserBase> listUsersByPage(Page<UserBase> page, @Param("queryParams")UserQuery queryParams);
}
