package com.smart.cms.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/13 14:18
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/system")
@Api(tags = "系统管理模块")
public class SystemController {

    @GetMapping("/test")
    public R<String> test() {
        return R.ok("测试");
    }
}
