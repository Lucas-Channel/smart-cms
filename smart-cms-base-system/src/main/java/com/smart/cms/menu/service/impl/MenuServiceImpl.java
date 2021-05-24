package com.smart.cms.menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.menu.mapper.MenuMapper;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.system.menu.MenuDTO;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 11:22
 * @Version: 1.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDTO> implements IMenuService {
}
