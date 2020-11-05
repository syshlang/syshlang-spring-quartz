/*
 * Copyright (c) 2019.
 * @File: CronUtils.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午6:41
 * @since:
 */

package com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

public class CronUtils {

    /**
     * 判断给定的Cron表达式是否有效
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }


    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static Date getNextExecution(String cronExpression)
    {
        try
        {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
