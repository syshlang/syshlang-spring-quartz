/*
 * Copyright (c) 2019. GRGBanking
 * @File: JobUtilFactoryUtil.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午6:41
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils;


import com.syshlang.quartz.core.common.*;
import com.syshlang.quartz.core.quartz.schedulerfactory.quartzjob.AsyncJobFactory;
import com.syshlang.quartz.core.quartz.schedulerfactory.quartzjob.SyncJobFactory;
import com.syshlang.quartz.model.DynamicQuartz;
import org.quartz.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.syshlang.quartz.core.common.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobUtilFactoryUtil {

    private static final Logger log = LoggerFactory.getLogger(JobUtilFactoryUtil.class);
    private static final String BEFORE_TASK_METHOD_NAME ="beforeTask";
    private static final String AFTER_TASK_METHOD_NAME ="afterTask";
    /**
     * 执行任务方法
     * @param jobExecutionContext
     * @param dynamicQuartz
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void invokeMethod(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz) {
        try {
            if (dynamicQuartz != null){
                Object bean = SpringUtils.getBean(dynamicQuartz.getQuartzGroup());
                String methodName = dynamicQuartz.getMethodName();
                String methodParams = dynamicQuartz.getMethodParams();
                if (StringUtil.isNotEmpty(methodName)){
                    if (StringUtil.isNotEmpty(methodParams)){
                        Method method = bean.getClass().getDeclaredMethod(methodName, DynamicQuartz.class,String.class);
                        method.invoke(bean, dynamicQuartz,methodParams);
                    }else{
                        Method method = bean.getClass().getDeclaredMethod(methodName,DynamicQuartz.class);
                        method.invoke(bean,dynamicQuartz);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException(MessageSourceHelper.getMessage("quartz.NoSuchMethodException"));
        } catch (IllegalAccessException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException(MessageSourceHelper.getMessage("quartz.IllegalAccessException"));
        } catch (InvocationTargetException e) {
            log.error(dynamicQuartz.getQuartzName(),e);
            throw  new QuartzException(MessageSourceHelper.getMessage("quartz.InvocationTargetException"));
        }
    }

    /**
     * 根据调度任务得到任务类（同步执行还是异步执行）
     * @param concurrent
     * @return
     */
    public static Class<? extends Job> getDynamicQuartzClass(String concurrent) {
        if (StringUtil.isEmpty(concurrent)){
            throw  new QuartzException(MessageSourceHelper.getMessage("quartz.concurrent.error"));
        }
        return  QuartzConstants.QUARTZ_JOB_ASYNC.equals(concurrent) ? AsyncJobFactory.class: SyncJobFactory.class;
    }

    /**
     *
     * @param id
     * @return
     */
    public static JobKey getJobKey(Long id) {
        return JobKey.jobKey(QuartzConstants.DYNAMIC_QUARTZ_KEY_PREFIX + id);
    }

    /**
     * 计划策略
     * @param dynamicQuartz
     * @param cronScheduleBuilder
     * @return
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(DynamicQuartz dynamicQuartz, CronScheduleBuilder cronScheduleBuilder) {
        String misfirePolicy = dynamicQuartz.getMisfirePolicy();
        if (StringUtil.isEmpty(misfirePolicy)){
            throw  new QuartzException(MessageSourceHelper.getMessage("quartz.misfirePolicy.error"));
        }
        if (QuartzConstants.MISFIRE_DEFAULT.equals(misfirePolicy)){
            return  cronScheduleBuilder;
        }
        if (QuartzConstants.MISFIRE_IGNORE_MISFIRES.equals(misfirePolicy)){
            return  cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
        }
        if (QuartzConstants.MISFIRE_FIRE_AND_PROCEED.equals(misfirePolicy)){
            return  cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        }
        if (QuartzConstants.MISFIRE_DO_NOTHING.equals(misfirePolicy)){
            return  cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        }
        return null;
    }

    /**
     * 获取触发器
     * @param id
     * @return
     */
    public static TriggerKey getTriggerKey(Long id) {
        return TriggerKey.triggerKey(QuartzConstants.DYNAMIC_QUARTZ_KEY_PREFIX + id);
    }

    /**
     * 生成表达式触发器
     * @param cronScheduleBuilder
     * @param dynamicQuartz
     * @return
     */
    public static CronTrigger getCronTrigger(CronScheduleBuilder cronScheduleBuilder, DynamicQuartz dynamicQuartz) {
        TriggerKey triggerKey = getTriggerKey(dynamicQuartz.getId());
        return TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
    }


    /**
     * 获取表达式触发器
     * @param scheduler
     * @param id
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long id) {
        try
        {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(id));
        }
        catch (SchedulerException e) {
            log.error("getCronTrigger 异常：", e);
        }
        return null;
    }
}
