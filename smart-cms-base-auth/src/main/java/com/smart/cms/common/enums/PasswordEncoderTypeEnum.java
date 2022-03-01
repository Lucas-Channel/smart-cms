package com.smart.cms.common.enums;

import lombok.Getter;

/**
 * @author Lucas
 * @description TODO
 * @createTime 2022/2/28 17:57
 */

public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }

}
