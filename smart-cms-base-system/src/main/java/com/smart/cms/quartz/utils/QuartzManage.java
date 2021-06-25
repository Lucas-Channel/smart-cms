package com.smart.cms.quartz.utils;

import com.smart.cms.system.job.QuartzJobDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@Component
public class QuartzManage {

    private static final String JOB_NAME = "TASK_";

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @SneakyThrows
    public void addJob(QuartzJobDTO quartzJob){
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(JOB_NAME + quartzJob.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JOB_NAME + quartzJob.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
                    .build();

            cronTrigger.getJobDataMap().put(QuartzJobDTO.JOB_KEY, quartzJob);

            //重置启动时间
            ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail,cronTrigger);

            // 暂停任务
            if (quartzJob.getIsPause() == 0) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            log.error("创建定时任务失败", e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 更新job cron表达式
     * @param quartzJob /
     */
    @SneakyThrows
    public void updateJobCron(QuartzJobDTO quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null){
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl)trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(QuartzJobDTO.JOB_KEY,quartzJob);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (quartzJob.getIsPause() == 0) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            log.error("更新定时任务失败", e);
            throw new Exception("更新定时任务失败");
        }

    }

    /**
     * 删除一个job
     * @param quartzJob /
     */
    @SneakyThrows
    public void deleteJob(QuartzJobDTO quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            log.error("删除定时任务失败", e);
            throw new Exception("删除定时任务失败");
        }
    }

    /**
     * 恢复一个job
     * @param quartzJob /
     */
    @SneakyThrows
    public void resumeJob(QuartzJobDTO quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                addJob(quartzJob);
            }
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e){
            log.error("恢复定时任务失败", e);
            throw new Exception("恢复定时任务失败");
        }
    }

    /**
     * 立即执行job
     * @param quartzJob /
     */
    @SneakyThrows
    public void runJobNow(QuartzJobDTO quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                addJob(quartzJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzJobDTO.JOB_KEY, quartzJob);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.triggerJob(jobKey,dataMap);
        } catch (Exception e){
            log.error("定时任务执行失败", e);
            throw new Exception("定时任务执行失败");
        }
    }

    /**
     * 暂停一个job
     * @param quartzJob /
     */
    public void pauseJob(QuartzJobDTO quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e){
            log.error("定时任务暂停失败", e);
            try {
                throw new Exception("定时任务暂停失败");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
