/*
 * Copyright (c) 2019. GRGBanking
 * @File: DynamicQuartz.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-19 上午9:32
 * @since:
 */

package com.syshlang.quartz.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DynamicQuartz implements Serializable {

	/**编号*/
    private Long id;

	/**任务名称*/
    private String quartzName;

	/**任务组名*/
    private String quartzGroup;

	/**任务方法*/
    private String methodName;

	/**方法参数*/
    private String methodParams;

	/**cron执行表达式*/
    private String cronExpression;

    /**同异步执行 0异步 1同步**/
    private String concurrent;

	/** 触发器 */
	private String jobTrigger;

	/**cron计划策略 0默认 1立即触发执行 2触发一次执行 3不触发立即执行*/
    private String misfirePolicy;

	/**任务状态 0正常 1暂停*/
    private String quartzStatus;

	/**创建人*/
    private String createUserName;

	/**创建时间*/
    private Date createDate;

	/**修改人*/
	private String updateUserName;

	/**修改时间*/
	private Date updateDate;

    /**数据级别 0高  1中  2低**/
    private String dataRank;

	/** 任务描述 */
	private String description;


}