package com.smart.cms.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.constant.GlobalConstants;
import com.smart.cms.permission.mapper.RolePermissionMapper;
import com.smart.cms.permission.service.IRolePermissionService;
import com.smart.cms.permission.vo.AllotedPermissionResponse;
import com.smart.cms.role.vo.RolePermissionVo;
import com.smart.cms.system.permission.PermissionDTO;
import com.smart.cms.system.permission.RolePermissionDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:53
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionDTO> implements IRolePermissionService {
    private final RedisTemplate redisTemplate;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Map<String, Object>> listRoleAllotedPermissionByRoleId(Long roleId) {
        List<AllotedPermissionResponse> allotedPermissionResponses = baseMapper.listRoleAllotedPermissionByRoleId(roleId);
        return getTree(allotedPermissionResponses);
    }

    @Override
    public boolean refreshPermRolesRules() {
        redisTemplate.delete(Arrays.asList(GlobalConstants.URL_PERM_ROLES_KEY, GlobalConstants.BTN_PERM_ROLES_KEY));
        List<PermissionDTO> permissionDTOS = baseMapper.listPermRoles();
        if (!CollectionUtils.isEmpty(permissionDTOS)) {
            // ?????????????????????-??????????????????redis?????????
            List<PermissionDTO> urlPermList = permissionDTOS.stream().filter(item -> StrUtil.isNotBlank(item.getUrlPerm())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.stream().forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
                redisTemplate.convertAndSend("cleanRoleLocalCache", "true");
            }
            // ?????????????????????-??????????????????redis?????????
            List<PermissionDTO> btnPermList = permissionDTOS.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getBtnPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(btnPermList)) {
                Map<String, List<String>> btnPermRoles = MapUtil.newHashMap();
                btnPermList.stream().forEach(item -> {
                    String perm = item.getBtnPerm();
                    List<String> roles = item.getRoles();
                    btnPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.BTN_PERM_ROLES_KEY, btnPermRoles);
            }
        }
        return true;
    }

    @Override
    public List<String> listRoleAllotedPermissionByRoles(List<String> roles) {
        Assert.notNull(roles, "????????????????????????");
        return rolePermissionMapper.listBtnPermByRoles(roles);
    }

    @Override
    public List<Long> listPermIds(Long menuId, Long roleId) {
        return this.baseMapper.listPermIds(menuId, roleId);
    }

    @Override
    public boolean saveRolePerms(RolePermissionVo rolePermissionVo) {
        Long menuId = rolePermissionVo.getMenuId();
        Long roleId = rolePermissionVo.getRoleId();
        List<Long> permIds = rolePermissionVo.getPermIds();

        List<Long> oldPermIds = this.listPermIds(menuId, roleId);

        // ??????????????????????????????
        List<Long> sortedPermIds = permIds.stream().sorted().collect(Collectors.toList());
        List<Long> sortedOldPermIds = oldPermIds.stream().sorted().collect(Collectors.toList());
        boolean permDataChangeFlag = !org.apache.commons.collections4.CollectionUtils.isEqualCollection(sortedPermIds, sortedOldPermIds);
        Assert.isTrue(permDataChangeFlag, "???????????????????????????????????????");

        // ?????????????????????????????????
        boolean updateFlag = false;
        if (CollectionUtil.isNotEmpty(oldPermIds)) {
            List<Long> removePermIds = oldPermIds.stream().filter(id -> !permIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removePermIds)) {
                updateFlag = this.remove(new LambdaQueryWrapper<RolePermissionDTO>()
                        .eq(RolePermissionDTO::getRoleId, roleId)
                        .in(RolePermissionDTO::getPermissionId, removePermIds));
            }
        }

        // ?????????????????????????????????
        if (CollectionUtil.isNotEmpty(permIds)) {
            List<Long> newPermIds = permIds.stream().filter(id -> !oldPermIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(newPermIds)) {
                List<RolePermissionDTO> rolePerms = new ArrayList<>();
                for (Long permId : newPermIds) {
                    RolePermissionDTO rolePerm = new RolePermissionDTO(roleId, permId);
                    rolePerms.add(rolePerm);
                }
                updateFlag = this.saveBatch(rolePerms);
            }
        }
        return updateFlag;
    }

    /**
     * ?????????
     * @param listTree
     * @return
     */
    public static List<Map<String, Object>> getTree(List<AllotedPermissionResponse> listTree) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(listTree)) {
            return list;
        }
        for(AllotedPermissionResponse entry: listTree) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(Objects.isNull(entry.getParentId())){ //?????????????????????
                mapArr.put("menuId", String.valueOf(entry.getMenuId()));
                mapArr.put("menuName", entry.getMenuName());
                mapArr.put("isAllot", entry.getIsAllot());
                mapArr.put("children", menuChild(entry.getMenuId(),listTree));  //?????????????????????
                list.add(mapArr);
            }
        }
        return list;
    }

    /**
     * ????????????
     * @param id
     * @param list
     * @return
     */
    public static List<?> menuChild(Long id,List<AllotedPermissionResponse> list){ //??????????????????
        List<Object> lists = new ArrayList<Object>();
        if (CollectionUtils.isEmpty(list)) {
            return lists;
        }
        for(AllotedPermissionResponse entry: list){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(Objects.equals(entry.getParentId(), id)){
                childArray.put("menuId", String.valueOf(entry.getMenuId()));
                childArray.put("menuName", entry.getMenuName());
                childArray.put("isAllot", entry.getIsAllot());
                childArray.put("children", menuChild(entry.getMenuId(),list));
                lists.add(childArray);
            }
        }
        return lists;
    }
}
