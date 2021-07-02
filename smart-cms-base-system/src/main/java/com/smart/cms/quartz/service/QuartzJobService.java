package com.smart.cms.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.cms.quartz.vo.QuartzJobRequestVO;
import com.smart.cms.system.job.QuartzJobDTO;
import com.smart.cms.utils.other.PageData;

import java.util.Set;

public interface QuartzJobService extends IService<QuartzJobDTO> {

    /**
     * 分页查询
     * @return /
     */
    R<IPage<QuartzJobDTO>> queryAll(QuartzJobRequestVO param, PageData pageData);

    /**
     * 创建
     * @param resources /
     */
    R saveOrUpdateBack(QuartzJobDTO resources);

    /**
     * 删除任务
     * @param ids /
     */
    R delete(Set<Long> ids);

    /**
     * 更改定时任务状态
     */
    R updateIsPause(Long id, int status);

    /**
     * 立即执行定时任务
     */
    R execution(Long id);

}
