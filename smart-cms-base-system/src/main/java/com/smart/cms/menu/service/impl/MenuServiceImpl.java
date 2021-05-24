package com.smart.cms.menu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.menu.mapper.MenuMapper;
import com.smart.cms.menu.service.IMenuService;
import com.smart.cms.menu.vo.MenuVo;
import com.smart.cms.system.menu.MenuDTO;
import com.smart.cms.utils.other.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
