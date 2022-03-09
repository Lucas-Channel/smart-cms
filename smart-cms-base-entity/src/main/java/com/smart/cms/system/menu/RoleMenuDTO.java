package com.smart.cms.system.menu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:38
 * @Version: 1.0
 */
@Data
@TableName("smart_system_role_menu")
@ApiModel(value = "角色Menu对象", description = "角色Menu对象")
@Accessors(chain = true)
public class RoleMenuDTO extends BaseEntityData implements Serializable {
    private Long roleId;

    private Long menuId;
}
