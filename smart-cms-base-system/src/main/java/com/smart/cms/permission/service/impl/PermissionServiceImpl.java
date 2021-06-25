package com.smart.cms.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.permission.mapper.PermissionMapper;
import com.smart.cms.permission.service.IPermissionService;
import com.smart.cms.permission.vo.AllotedPermissionResponse;
import com.smart.cms.system.permission.PermissionDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/6/25 10:53
 * @Version: 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDTO> implements IPermissionService {

    @Override
    public List<Map<String, Object>> listRoleAllotedPermissionByRoleId(Long roleId) {
        List<AllotedPermissionResponse> allotedPermissionResponses = baseMapper.listRoleAllotedPermissionByRoleId(roleId);
        return getTree(allotedPermissionResponses);
    }

    /**
     * 递归树
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
            if(Objects.isNull(entry.getParentId())){ //判断是否为父极
                mapArr.put("menuId", String.valueOf(entry.getMenuId()));
                mapArr.put("menuName", entry.getMenuName());
                mapArr.put("isAllot", entry.getIsAllot());
                mapArr.put("children", menuChild(entry.getMenuId(),listTree));  //去子集查找遍历
                list.add(mapArr);
            }
        }
        return list;
    }

    /**
     * 递归子树
     * @param id
     * @param list
     * @return
     */
    public static List<?> menuChild(Long id,List<AllotedPermissionResponse> list){ //子集查找遍历
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
