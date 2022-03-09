package com.smart.cms.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.system.menu.RoleMenuDTO;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:41
 * @Version: 1.0
 */
public interface IRoleMenuService extends IService<RoleMenuDTO> {
    List<Long> listMenuIds(Long roleId);

    boolean update(Long roleId, List<Long> menuIds);
}
