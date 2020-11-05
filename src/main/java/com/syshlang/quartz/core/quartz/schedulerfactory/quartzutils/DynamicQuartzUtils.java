/*
 * Copyright (c) 2019. GRGBanking
 * @File: DynamicQuartzUtils.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午6:45
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils;


import com.syshlang.quartz.core.common.QuartzConstants;
import com.syshlang.quartz.core.common.QuartzException;
import com.syshlang.quartz.model.DynamicQuartz;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicQuartzUtils {
    private static final Logger log = LoggerFactory.getLogger(DynamicQuartzUtils.class);


    /**
     *  动态创建定时任务
     * @param scheduler
     * @param dynamicQuartz
     * @throws SchedulerException
     */
    public static void createDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz)
    {
        Class<? extends Job> jobClass = JobUtilFactoryUtil.getDynamicQuartzClass(dynamicQuartz.getConcurrent());
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(JobUtilFactoryUtil.getJobKey(dynamicQuartz.getId())).build();
        String cronExpression = dynamicQuartz.getCronExpression();
        if (!CronUtils.isValid(cronExpression)){
            throw  new QuartzException("quartz.cronExpression.error");
        }
        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
       // cronScheduleBuilder = JobUtilFactoryUtil.handleCronScheduleMisfirePolicy(dynamicQuartz, cronScheduleBuilder);
        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = JobUtilFactoryUtil.getCronTrigger(cronScheduleBuilder,dynamicQuartz);
        String jobTrigger = trigger.getKey().getName();
        dynamicQuartz.setJobTrigger(jobTrigger);
        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(QuartzConstants.TASK_PROPERTIES_KEY, dynamicQuartz);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            // 暂停任务
            if (QuartzConstants.QUARTZ_STATUS_PAUSE.equals(dynamicQuartz.getQuartzStatus())) {
                pauseDynamicQuartzJob(scheduler, dynamicQuartz);
            }
        } catch (SchedulerException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.create.error",dynamicQuartz.getQuartzName());
        }
    }

    /**
     * 删除定时任务
     * @param scheduler
     * @param dynamicQuartz
     */
    public static void deleteDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz) {
        try {
            JobKey jobKey = JobUtilFactoryUtil.getJobKey(dynamicQuartz.getId());
            // 判断是否存在
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.delete.error",dynamicQuartz.getQuartzName());
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz) {
        try {
            // 先移除，然后做更新操作
            deleteDynamicQuartzJob(scheduler, dynamicQuartz);
            createDynamicQuartzJob(scheduler, dynamicQuartz);
            // 暂停任务
            if (QuartzConstants.QUARTZ_STATUS_PAUSE.equals(dynamicQuartz.getQuartzStatus())) {
                pauseDynamicQuartzJob(scheduler, dynamicQuartz);
            }
        } catch (QuartzException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.update.error",dynamicQuartz.getQuartzName());
        }
    }

    /**
     * 暂停任务
     * @param scheduler
     * @param dynamicQuartz
     */
    public static void pauseDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz) {
        try {
            scheduler.pauseJob(JobUtilFactoryUtil.getJobKey(dynamicQuartz.getId()));
        } catch (SchedulerException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.pause.error",dynamicQuartz.getQuartzName());
        }
    }

    /**
     * 恢复任务
     * @param scheduler
     * @param dynamicQuartz
     */
    public static void resumeDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz) {
        try {
            scheduler.resumeJob(JobUtilFactoryUtil.getJobKey(dynamicQuartz.getId()));
        } catch (SchedulerException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.resume.error",dynamicQuartz.getQuartzName());
        }
    }

    /**
     *  立即执行任务
     * @param scheduler
     * @param dynamicQuartz
     */
    public static void runDynamicQuartzJob(Scheduler scheduler, DynamicQuartz dynamicQuartz) {
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzConstants.TASK_PROPERTIES_KEY, dynamicQuartz);
            scheduler.triggerJob(JobUtilFactoryUtil.getJobKey(dynamicQuartz.getId()), dataMap);
        } catch (SchedulerException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException("quartz.run.error",dynamicQuartz.getQuartzName());
        }
    }

}
