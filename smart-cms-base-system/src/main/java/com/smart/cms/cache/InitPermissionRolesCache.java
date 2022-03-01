package com.smart.cms.cache;

import com.smart.cms.permission.service.IRolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TODO 服务启动完成加载角色权限到redis缓存里面
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 15:53
 * @Version: 1.0
 */
@Component
@AllArgsConstructor
public class InitPermissionRolesCache implements CommandLineRunner {

    private IRolePermissionService permissionService;
    @Override
    public void run(String... args) throws Exception {
        permissionService.refreshPermRolesRules();
    }
}
