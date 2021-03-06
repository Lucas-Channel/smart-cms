package com.smart.cms.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.account.mapper.UserMapper;
import com.smart.cms.account.service.IUserService;
import com.smart.cms.account.vo.UserQuery;
import com.smart.cms.constant.GlobalConstants;
import com.smart.cms.role.service.IUserRoleService;
import com.smart.cms.system.role.UserRole;
import com.smart.cms.user.UserBase;
import com.smart.cms.utils.other.PageData;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:16
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBase> implements IUserService {
    private PasswordEncoder passwordEncoder;
    private IUserRoleService userRoleService;
    @Override
    public IPage<UserBase> listUsersByPage(UserQuery queryParams, PageData pageData) {
        Page<UserBase> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<UserBase> list = baseMapper.listUsersByPage(page, queryParams);
        return list;
    }

    @Override
    public boolean saveUser(UserBase user) {
        user.setPassword(passwordEncoder.encode(StringUtils.isEmpty(user.getPassword()) ? GlobalConstants.DEFAULT_USER_PASSWORD : user.getPassword()));
        boolean result = this.save(user);
        if (result) {
            List<Long> roleIds = user.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<UserRole> userRoleList = new ArrayList<>();
                roleIds.forEach(roleId -> userRoleList.add(new UserRole().setUserId(user.getId()).setRoleId(roleId)));
                result = userRoleService.saveBatch(userRoleList);
            }
        }
        return result;
    }

    @Override
    public boolean updateUser(UserBase user) {
        // ?????????????????????ID??????
        List<Long> oldRoleIds = userRoleService.list(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, user.getId())).stream()
                .map(item -> item.getRoleId())
                .collect(Collectors.toList());

        // ??????????????????ID??????
        List<Long> newRoleIds = user.getRoleIds();

        // ???????????????????????????ID??????
        List<Long> addRoleIds = newRoleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(addRoleIds)) {
            List<UserRole> addUserRoleList = new ArrayList<>();
            addRoleIds.forEach(roleId -> {
                addUserRoleList.add(new UserRole().setUserId(user.getId()).setRoleId(roleId));
            });
            userRoleService.saveBatch(addUserRoleList);
        }

        // ??????????????????????????????ID??????
        List<Long> removeRoleIds = oldRoleIds.stream().filter(roleId -> !newRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeRoleIds)) {
            removeRoleIds.forEach(roleId -> {
                userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()).eq(UserRole::getRoleId, roleId));
            });
        }

        // ??????????????????
        return this.updateById(user);
    }

}
