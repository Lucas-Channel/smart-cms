package com.smart.cms.permission.controller;

import com.smart.cms.permission.service.IPermissionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 16:17
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/permission")
@Api(value = "权限控制器", tags = "权限控制器")
public class PermissionController {

    private IPermissionService permissionService;


}
