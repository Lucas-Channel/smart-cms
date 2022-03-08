package com.smart.cms.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.constant.GlobalConstants;
import com.smart.cms.menu.mapper.MenuMapper;
import com.smart.cms.menu.mapper.RoleMenuMapper;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.IdLabelVO;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.menu.vo.ValueLabelVO;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.system.menu.MenuDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 11:22
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDTO> implements IMenuService {

    private RoleMenuMapper roleMenuMapper;

    private IRolePermissionService permissionService;

    @Override
    public boolean saveMenu(MenuDTO menuDTO) {
        String component = menuDTO.getComponentPath();
        String path = menuDTO.getRoutePath();

        if (StrUtil.isBlank(path)) { // 非外链
            if ("Layout".equals(component)) {
                menuDTO.setRoutePath("/" + IdUtil.simpleUUID());
            } else {
                menuDTO.setRoutePath(component.replaceAll("/", "_"));
            }
        }

        boolean result = this.save(menuDTO);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    @Override
    public boolean updateMenu(MenuDTO menuDTO) {
        String component = menuDTO.getComponentPath();

        // 根据组件路径生成相对路径path
        MenuDTO dbMenu = this.getById(menuDTO.getId());
        if (StrUtil.isNotBlank(component)) {
            if (!component.equals(dbMenu.getComponentPath())) {
                if ("Layout".equals(component)) {
                    menuDTO.setRoutePath("/" + IdUtil.simpleUUID());
                } else {
                    menuDTO.setRoutePath(component.replaceAll("/", "_"));
                }
            }
        }

        boolean result = this.updateById(menuDTO);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    @Override
    public List<RouteVO> listRoute() {
        List<MenuDTO> menuList = roleMenuMapper.listMenusWithRole();
        List<RouteVO> list = recursionRoute(GlobalConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List<MenuDTO> listTable(String name) {
        List<MenuDTO> menuList = this.lambdaQuery().like(StrUtil.isNotBlank(name), MenuDTO::getName, name).orderByAsc(MenuDTO::getSort).list();
        return recursion(menuList);
    }

    @Override
    public List<ValueLabelVO> listSelect() {
        List<MenuDTO> menuList = this.list(new LambdaQueryWrapper<MenuDTO>().orderByAsc(MenuDTO::getSort));
        List<ValueLabelVO> menuSelectList = recursionSelectList(GlobalConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }

    @Override
    public List<IdLabelVO> listTreeSelect() {
        List<MenuDTO> menuList = this.list(new LambdaQueryWrapper<MenuDTO>().orderByAsc(MenuDTO::getSort));
        List<IdLabelVO> menuSelectList = recursionTreeSelectList(GlobalConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<ValueLabelVO> recursionSelectList(Long parentId, List<MenuDTO> menuList) {
        List<ValueLabelVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    ValueLabelVO valueLabelVO = new ValueLabelVO(menu.getId(), menu.getName());
                    List<ValueLabelVO> children = recursionSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        valueLabelVO.setChildren(children);
                    }
                    menuSelectList.add(valueLabelVO);
                });
        return menuSelectList;
    }

    /**
     * 递归生成菜单下拉(TreeSelect)层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<IdLabelVO> recursionTreeSelectList(Long parentId, List<MenuDTO> menuList) {
        List<IdLabelVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    IdLabelVO idLabelVO = new IdLabelVO(menu.getId(), menu.getName());
                    List<IdLabelVO> children = recursionTreeSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        idLabelVO.setChildren(children);
                    }
                    menuSelectList.add(idLabelVO);
                });
        return menuSelectList;
    }


    /**
     * 递归生成菜单表格层级列表
     *
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private static List<MenuDTO> recursion(List<MenuDTO> menuList) {
        List<MenuDTO> menuTableList = new ArrayList<>();
        // 保存所有节点的 id
        Set<Long> nodeIdSet = menuList.stream()
                .map(MenuDTO::getId)
                .collect(Collectors.toSet());
        for (MenuDTO sysMenu : menuList) {
            // 不在节点 id 集合中存在的 id 即为顶级节点 id, 递归生成列表
            Long parentId = sysMenu.getParentId();
            if (!nodeIdSet.contains(parentId)) {
                menuTableList.addAll(recursionTableList(parentId, menuList));
                nodeIdSet.add(parentId);
            }
        }
        // 如果结果列表为空说明所有的节点都是独立分散的, 直接转换后返回
        if (menuTableList.isEmpty()) {
            return menuList.stream()
                    .map(item -> {
                        MenuDTO menuVO = new MenuDTO();
                        BeanUtil.copyProperties(item, menuVO);
                        return menuVO;
                    })
                    .collect(Collectors.toList());
        }
        return menuTableList;
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<MenuDTO> recursionTableList(Long parentId, List<MenuDTO> menuList) {
        List<MenuDTO> menuTableList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    MenuDTO menuVO = new MenuDTO();
                    BeanUtil.copyProperties(menu, menuVO);
                    List<MenuDTO> children = recursionTableList(menu.getId(), menuList);

                    if (CollectionUtil.isNotEmpty(children)) {
                        menuVO.setChildren(children);
                    }
                    menuTableList.add(menuVO);
                });
        return menuTableList;
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
