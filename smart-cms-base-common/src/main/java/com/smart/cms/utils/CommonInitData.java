package com.smart.cms.utils;

import com.smart.cms.base.BaseEntityData;

import java.util.Date;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 10:44
 * @Version: 1.0
 */
public class CommonInitData {
    public static void initData(BaseEntityData item) {
        if (null != item.getId()) {
            item.setCreateTime(new Date());
            item.setCreatorCode(AuthUserInfo.getLoginUserName());
        }
        item.setUpdateTime(new Date());
        item.setUpdaterCode(AuthUserInfo.getLoginUserName());
        item.setDelFlag(0);
    }
}
