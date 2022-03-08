package com.smart.cms.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.menu.vo.IdLabelVO;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.menu.vo.ValueLabelVO;
import com.smart.cms.system.menu.MenuDTO;

import java.util.List;

/**
 * 服务类
 *
 * @author Lucas
 */
public interface IMenuService extends IService<MenuDTO> {

    boolean saveMenu(MenuDTO menuDTO);
    boolean updateMenu(MenuDTO menuDTO);

    List<RouteVO> listRoute();

    List<MenuDTO> listTable(String name);

    /**
     * 菜单下拉(Select)层级列表
     *
     * @return
     */
    List<ValueLabelVO> listSelect();

    /**
     * 菜单下拉(TreeSelect)层级列表
     *
     * @return
     */
    List<IdLabelVO> listTreeSelect();
}
