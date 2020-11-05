/*
 * Copyright (c) 2019.
 * @File: SyncJobFactory.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午6:37
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzjob;


import com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils.JobUtilFactoryUtil;
import com.syshlang.quartz.model.DynamicQuartz;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 同步任务 (不允许并发执行）
 * @author sunys
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz){
        JobUtilFactoryUtil.invokeMethod(jobExecutionContext,dynamicQuartz);
    }
}
