package com.smart.cms.invorg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.invorg.service.InvOrgService;
import com.smart.cms.service.product.InvOrg;
import com.smart.cms.utils.AuthUserInfo;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/23 17:22
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/invOrg")
@Api(value = "仓库控制器", tags = "仓库控制器")
public class InvOrgController {

    private InvOrgService invOrgService;

    @GetMapping("/listInvOrgByPage")
    @ApiOperation(value = "分页-列表", notes = "传入对象")
    public R<IPage<InvOrg>> listInvOrgByPage(InvOrg invOrg, PageData pageData) {
        QueryWrapper<InvOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(invOrg.getInvOrgCode())) {
            queryWrapper.like("inv_org_code", invOrg.getInvOrgCode());
        }
        if (StringUtils.isNotBlank(invOrg.getInvOrgName())) {
            queryWrapper.like("inv_org_name", invOrg.getInvOrgName());
        }
        if (StringUtils.isNotBlank(invOrg.getEnableFlag())) {
            queryWrapper.like("enable_flag", invOrg.getEnableFlag());
        }
        Page<InvOrg> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<InvOrg> pages = invOrgService.page(page, queryWrapper);
        return R.ok(pages);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增", notes = "传入对象")
    public R saveOrUpdate(@RequestBody InvOrg invOrg) {
        initData(invOrg);
        boolean b = invOrgService.saveOrUpdate(invOrg);
        return b ? R.ok("操作成功") : R.failed("操作成功");
    }

    protected void initData(InvOrg invOrg) {
        if (null != invOrg.getId()) {
            invOrg.setCreateTime(new Date());
            invOrg.setCreatorCode(AuthUserInfo.getLoginUserName());
        }
        invOrg.setUpdateTime(new Date());
        invOrg.setUpdaterCode(AuthUserInfo.getLoginUserName());
        invOrg.setDelFlag(0);
    }
}
