package com.smart.cms.user;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.smart.cms.constant.SecurityConstants;
import com.smart.cms.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/4 16:02
 * @Version: 1.0
 */
@Slf4j
public class AuthUser {
    public static Long getUserId() {
        Long userId = null;
        JSONObject jsonObject = JwtUtils.getJwtPayload();
        if (!Objects.isNull(jsonObject)) {
            userId = jsonObject.getLong("userId");
        }
        return userId;
    }

    public static String getUsername() {
        String username =  JwtUtils.getJwtPayload().getStr(SecurityConstants.USER_NAME_KEY);
        return username;
    }

    public static List<String> getRoles() {
        List<String> roles;
        JSONObject payload =  JwtUtils.getJwtPayload();
        if (payload.containsKey(SecurityConstants.JWT_AUTHORITIES_KEY)) {
            roles = payload.getJSONArray(SecurityConstants.JWT_AUTHORITIES_KEY).toList(String.class);
        } else {
            roles = Collections.emptyList();
        }
        return roles;
    }

    public static boolean isAdmin() {
        List<String> roles = getRoles();
        return CollectionUtil.isNotEmpty(roles) && roles.contains("ADMIN");
    }
}
