package com.smart.cms.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.quartz.service.QuartzJobService;
import com.smart.cms.quartz.vo.QuartzJobRequestVO;
import com.smart.cms.system.job.QuartzJobDTO;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
@Api(tags = "系统:定时任务管理")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";
    private final QuartzJobService quartzJobService;

    @ApiOperation("查询定时任务")
    @GetMapping
    public R<IPage<QuartzJobDTO>> query(@RequestParam QuartzJobRequestVO param, PageData pageData){
        return quartzJobService.queryAll(param, pageData);
    }

    @ApiOperation("新增定时任务")
    @PostMapping
    public R create(@RequestBody QuartzJobDTO resources){
        return quartzJobService.saveOrUpdateBack(resources);
    }

    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/updateIsPause/{id}/{status}")
    public R updateIsPause(@PathVariable Long id, @PathVariable int status){
        return quartzJobService.updateIsPause(id, status);
    }

    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    public R execution(@PathVariable Long id){
        return quartzJobService.execution(id);
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping("/deleteBatchByIds")
    public R delete(@RequestBody Set<Long> ids){
        return quartzJobService.delete(ids);
    }
}
