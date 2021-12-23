package com.smart.cms.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 获取登录信息
 *
 * @Author: huilai.huang
 * @Date: 2021/12/23 16:35
 * @Version: 1.0
 */
public class AuthUserInfo {
    public static Long getLoginUserId() {return null;}

    public static String getLoginUserName() {return null;}

    public static String getLoginUserRole() {return null;}

    public static String getToken() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        return StringUtils.substringAfter(authorization, "bearer ");
    }
}
