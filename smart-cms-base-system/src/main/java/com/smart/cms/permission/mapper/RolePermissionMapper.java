/*
 * 使用注解类sql，如果是需要动态参数写法需要加@Select({"<script>sql</script>"}) 反之直接写sql：@Select(sql)
 */
package com.smart.cms.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.cms.permission.vo.AllotedPermissionResponse;
import com.smart.cms.system.permission.PermissionDTO;
import com.smart.cms.system.permission.RolePermissionDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TODO 此文件顶部有关于注解类sql的说明
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:44
 * @Version: 1.0
 */
public interface RolePermissionMapper extends BaseMapper<RolePermissionDTO> {

    @Select("SELECT meu.id menu_id, meu.parent_id, meu.name menu_name, if(rp.id is null, 0, 1) is_allot from smart_system_menu meu\n" +
            "LEFT JOIN smart_system_role_permission rp ON (rp.del_flag = 0 AND rp.role_id = #{roleId} AND rp.menu_id = meu.id)\n" +
            "WHERE meu.del_flag = 0")
    List<AllotedPermissionResponse> listRoleAllotedPermissionByRoleId(@Param("roleId") Long roleId);

    List<PermissionDTO> listPermRoles();

    List<String> listBtnPermByRoles(@Param("roles") List<String> roles);

    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    List<Long> listPermIds(Long menuId, Long roleId);
}
