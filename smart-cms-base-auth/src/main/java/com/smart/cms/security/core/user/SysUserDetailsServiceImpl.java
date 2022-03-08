package com.smart.cms.security.core.user;

import com.smart.cms.common.ResultCode;
import com.smart.cms.mapper.UserMapper;
import com.smart.cms.user.UserBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户体系业务类
 *
 */
@Service("sysUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetails userDetails = null;
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("username", username);
        paramsMap.put("del_flag", 0);
        List<UserBase> userBases = userMapper.selectByMap(paramsMap);
        userBases.forEach(it -> {
            it.setRoles(userMapper.listByUserId(it.getId()));
        });
        if (!CollectionUtils.isEmpty(userBases)) {
            userDetails = new SysUserDetails(userBases.get(0));
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUserDetails userDetails = null;
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("mobile", mobile);
        paramsMap.put("del_flag", 0);
        paramsMap.put("status", 1);
        List<UserBase> userBases = userMapper.selectByMap(paramsMap);
        userBases.forEach(it -> {
            it.setRoles(userMapper.listByUserId(it.getId()));
        });
        if (!CollectionUtils.isEmpty(userBases)) {
            userDetails = new SysUserDetails(userBases.get(0));
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

}
