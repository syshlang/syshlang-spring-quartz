/*
 * Copyright (c) 2019. GRGBanking
 * @File: AsyncJobFactory.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午4:10
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzjob;



import com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils.JobUtilFactoryUtil;
import com.syshlang.quartz.model.DynamicQuartz;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 异步任务 (允许并发执行）
 */
public class AsyncJobFactory extends AbstractQuartzJob {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncJobFactory.class);

    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext, DynamicQuartz dynamicQuartz){
        JobUtilFactoryUtil.invokeMethod(jobExecutionContext,dynamicQuartz);
    }
}
