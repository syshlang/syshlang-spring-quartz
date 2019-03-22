/*
 * Copyright (c) 2019. GRGBanking
 * @File: QuartzConstants.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午7:17
 * @since:
 */

package com.syshlang.quartz.core.common;

/**
 *
 */
public class QuartzConstants {

    /** Jobkey */
    public static final String DYNAMIC_QUARTZ_KEY_PREFIX = "EBM_DYNAMIC_QUARTZ";

    /** 执行目标key */
    public static final String TASK_PROPERTIES_KEY = "TASK_PROPERTIES_KEY";

    //异步
    public static final String QUARTZ_JOB_ASYNC = "0";

    // 同步
    public static final String QUARTZ_JOB_SYNC = "1";


    /** 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";


    /**
     * 任务状态正常
     */
    public static final String QUARTZ_STATUS_NORMAL="0";
    /**
     * 任务状态暂停
     */
    public static final String QUARTZ_STATUS_PAUSE="1";
}
