package com.smart.cms.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.menu.vo.RouteVO;
import com.smart.cms.system.menu.MenuDTO;

import java.util.List;

/**
 * 服务类
 *
 * @author Lucas
 */
public interface IMenuService extends IService<MenuDTO> {

    List<RouteVO> listRoute();
}
