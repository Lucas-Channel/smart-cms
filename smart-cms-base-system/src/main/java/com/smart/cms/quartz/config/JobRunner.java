//package com.smart.cms.quartz.config;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.smart.cms.quartz.mapper.QuartzMapper;
//import com.smart.cms.system.job.QuartzJobDTO;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * TODO 项目启动执行类
// *
// * @Author: huilai.huang
// * @Date: 2021/6/25 18:01
// * @Version: 1.0
// */
//@Component
//@RequiredArgsConstructor
//public class JobRunner implements ApplicationRunner {
//    private static final Logger log = LoggerFactory.getLogger(JobRunner.class);
//    private final QuartzMapper quartzMapper;
////    private final QuartzManage quartzManage;
//
//    /**
//     * 项目启动时重新激活启用的定时任务
//     *
//     * @param applicationArguments /
//     */
//    @Override
//    public void run(ApplicationArguments applicationArguments) {
//        log.info("--------------------注入定时任务---------------------");
//        List<QuartzJobDTO> quartzJobs = quartzMapper.selectList(new QueryWrapper<QuartzJobDTO>().eq("del_flag", 0).eq("is_pause", 1));
////        quartzJobs.forEach(quartzManage::addJob);
//        log.info("--------------------定时任务注入完成---------------------");
//    }
//}
