package com.smart.cms.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.system.menu.MenuDTO;
import com.smart.cms.system.menu.RoleMenuDTO;
import org.apache.ibatis.annotations.Select;

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

    @Select(" SELECT " +
            " 	t1.menu_id  " +
            " FROM " +
            " 	smart_system_role_menu t1 " +
            " 	INNER JOIN smart_system_menu t2 ON t1.menu_id = t2.id  " +
            " WHERE role_id =#{roleId}")
    List<Long> listMenuIds(Long roleId);
}
