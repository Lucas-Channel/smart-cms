package com.smart.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.user.UserBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 8:55
 * @Version: 1.0
 */
public interface UserMapper extends BaseMapper<UserBase> {
    List<String> listByUserId(@Param("userId") Long userId);
}
