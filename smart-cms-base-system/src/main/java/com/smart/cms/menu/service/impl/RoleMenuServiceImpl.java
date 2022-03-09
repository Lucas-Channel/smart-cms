package com.smart.cms.menu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.menu.mapper.RoleMenuMapper;
import com.smart.cms.menu.service.IRoleMenuService;
import com.smart.cms.system.menu.RoleMenuDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:41
 * @Version: 1.0
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDTO> implements IRoleMenuService {
    @Override
    public List<Long> listMenuIds(Long roleId) {
        return this.baseMapper.listMenuIds(roleId);
    }

    @Override
    public boolean update(Long roleId, List<Long> menuIds) {
        boolean result = true;
        List<Long> dbMenuIds = this.list(new LambdaQueryWrapper<RoleMenuDTO>().eq(RoleMenuDTO::getRoleId, roleId))
                .stream().map(item -> item.getMenuId()).collect(Collectors.toList());

        // 删除数据库存在此次提交不存在的
        if (CollectionUtil.isNotEmpty(dbMenuIds)) {
            List<Long> removeMenuIds = dbMenuIds.stream().filter(id -> !menuIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removeMenuIds)) {
                this.remove(new LambdaQueryWrapper<RoleMenuDTO>().eq(RoleMenuDTO::getRoleId, roleId)
                        .in(RoleMenuDTO::getMenuId, removeMenuIds));
            }
        }

        // 插入数据库不存在的
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<Long> insertMenuIds = menuIds.stream().filter(id -> !dbMenuIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(insertMenuIds)) {
                List<RoleMenuDTO> roleMenus = new ArrayList<>();
                for (Long insertMenuId : insertMenuIds) {
                    RoleMenuDTO roleMenu = new RoleMenuDTO().setRoleId(roleId).setMenuId(insertMenuId);
                    roleMenus.add(roleMenu);
                }
                result = this.saveBatch(roleMenus);
            }
        }
        return result;
    }
}
