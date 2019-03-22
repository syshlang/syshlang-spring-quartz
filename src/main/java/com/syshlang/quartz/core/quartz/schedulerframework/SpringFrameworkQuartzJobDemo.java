/*
 * Copyright (c) 2019. GRGBanking
 * @File: SpringFrameworkQuartzJobDemo.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-18 下午3:30
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerframework;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 框架定时器
 * 采用spring配置的方式定时作业，作业内容的实现可以写在任何地方，只要保证@Service注解和所对应的的方法存在即可
 * 与电子锁业务相关的建议写在service中
 * @author sunys
 *
 */
@Service("springFrameworkQuartzJobDemo")
public class SpringFrameworkQuartzJobDemo implements Job {
	
	public void springFrameworkQuartzJobDemoJob() {
		System.out.println("框架定时器示例：我是定时器【quartzJob】,可在ebm-quartz\\src\\main\\resources\\META-INF\\applicationContext-job.xml中进行配置："+new Date().toString());
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		springFrameworkQuartzJobDemoJob();
	}
}