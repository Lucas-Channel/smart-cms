package com.smart.cms.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.quartz.mapper.QuartzMapper;
import com.smart.cms.quartz.service.QuartzJobService;
import com.smart.cms.quartz.utils.QuartzManage;
import com.smart.cms.quartz.vo.QuartzJobRequestVO;
import com.smart.cms.system.dict.DictDTO;
import com.smart.cms.system.job.QuartzJobDTO;
import com.smart.cms.utils.other.PageData;
import com.smart.cms.utils.redis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service(value = "quartzJobService")
public class QuartzJobServiceImpl  extends ServiceImpl<QuartzMapper, QuartzJobDTO> implements QuartzJobService {

    private final QuartzManage quartzManage;
    private final RedisUtils redisUtils;

    @Override
    public R<IPage<QuartzJobDTO>> queryAll(QuartzJobRequestVO param, PageData pageData){
        QueryWrapper<QuartzJobDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotBlank(param.getJobName())) {
            queryWrapper.like("job_name", param.getJobName());
        }
        if (StringUtils.isNotBlank(String.valueOf(param.getIsPause()))) {
            queryWrapper.like("is_pause", param.getIsPause());
        }
        Page<QuartzJobDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        IPage<QuartzJobDTO> pages = this.page(page, queryWrapper);
        return R.ok(pages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveOrUpdateBack(QuartzJobDTO resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            // cron表达式格式错误
            return R.failed("cron表达式格式错误");
        }
        boolean r = this.saveOrUpdate(resources);
        return r ? R.ok("成功") : R.failed("失败");
    }

    @Override
    public R updateIsPause(Long id, int status) {
        QuartzJobDTO quartzJob = baseMapper.selectById(id);
        if (status == 0) {
            // 暂停定时任务
            quartzManage.pauseJob(quartzJob);
        } else {
            // 开启定时任务
            quartzManage.resumeJob(quartzJob);
        }
        quartzJob.setIsPause(status);
        int row = baseMapper.updateById(quartzJob);
        return row > 0 ? R.ok("操作成功") : R.failed("操作失败");
    }

    @Override
    public R execution(Long id) {
        QuartzJobDTO quartzJob = this.lambdaQuery().eq(QuartzJobDTO::getId, id).eq(QuartzJobDTO::getDelFlag, 0).one();
        quartzManage.runJobNow(quartzJob);
        return R.ok("成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R delete(Set<Long> ids) {
        for (Long id : ids) {
            QuartzJobDTO quartzJob = this.lambdaQuery().eq(QuartzJobDTO::getId, id).eq(QuartzJobDTO::getDelFlag, 0).one();
            quartzManage.deleteJob(quartzJob);
        }
       boolean del = this.removeByIds(ids);
        return del ? R.ok("成功") : R.failed("失败");
    }

}
