/*
 * Copyright (c) 2019. GRGBanking
 * @File: AbstractQuartzJob.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午3:50
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzjob;



import com.syshlang.quartz.core.common.QuartzConstants;
import com.syshlang.quartz.model.DynamicQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Date;

public abstract class AbstractQuartzJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DynamicQuartz dynamicQuartz = (DynamicQuartz) jobExecutionContext.getMergedJobDataMap().get(QuartzConstants.TASK_PROPERTIES_KEY);
        try {
            if (dynamicQuartz != null) {
                before(jobExecutionContext, dynamicQuartz);
                doExecute(jobExecutionContext, dynamicQuartz);
            }
            after(jobExecutionContext, dynamicQuartz, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(jobExecutionContext, dynamicQuartz, e.getMessage());
        }
    }


    /**
     * 执行方法
     * @param jobExecutionContext
     * @param dynamicQuartz
     */
    protected abstract void doExecute(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz);

    protected void before(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz){

    }
    private void after(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz, String message) {
    }



}
