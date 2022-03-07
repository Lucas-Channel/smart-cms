package com.smart.cms.menu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.constant.GlobalConstants;
import com.smart.cms.menu.mapper.MenuMapper;
import com.smart.cms.menu.mapper.RoleMenuMapper;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.system.menu.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 11:22
 * @Version: 1.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDTO> implements IMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RouteVO> listRoute() {
        List<MenuDTO> menuList = roleMenuMapper.listMenusWithRole();
        List<RouteVO> list = recursionRoute(0l, menuList);
        return list;
    }

    private List<RouteVO> recursionRoute(Long parentId, List<MenuDTO> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouteVO routeVO = new RouteVO();
                    routeVO.setName(menu.getId() + "");
                    routeVO.setPath(menu.getRoutePath());
                    routeVO.setComponent(menu.getComponentPath());
                    RouteVO.Meta meta = new RouteVO.Meta(menu.getName(), menu.getIcon(), menu.getRoles());
                    routeVO.setMeta(meta);
                    // 菜单显示隐藏
                    routeVO.setHidden(!GlobalConstants.STATUS_YES.equals(menu.getStatus()));
                    List<RouteVO> children = recursionRoute(menu.getId(), menuList);
                    routeVO.setChildren(children);
                    if (CollectionUtil.isNotEmpty(children)) {
                        routeVO.setAlwaysShow(Boolean.TRUE);
                    }
                    list.add(routeVO);
                }));
        return list;

    }
}
