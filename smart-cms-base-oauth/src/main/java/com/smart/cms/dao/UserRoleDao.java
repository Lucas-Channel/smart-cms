package com.smart.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.system.role.UserRole;

import java.util.List;

public interface UserRoleDao extends BaseMapper<UserRole> {

    /**
     * 通过用户id 查询出角色id
     *
     * @param userId 用户id
     * @return java.util.List<UserRolePo>
     * @author LiuYongTao
     * @date 2018/11/5 16:23
     */
    List<UserRole> findRoleIdByUserIdAndDelFlag(Long userId, int delFlag);
}
