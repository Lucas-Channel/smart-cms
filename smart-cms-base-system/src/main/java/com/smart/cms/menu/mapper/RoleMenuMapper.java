package com.smart.cms.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.system.menu.MenuDTO;
import com.smart.cms.system.menu.RoleMenuDTO;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:40
 * @Version: 1.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenuDTO> {
    List<MenuDTO> listMenusWithRole();
}
