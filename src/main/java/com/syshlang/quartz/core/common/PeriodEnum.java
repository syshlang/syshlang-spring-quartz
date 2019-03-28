/*
 * Copyright (c) 2019. GRGBanking
 * @File: PeriodEnum.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-28 下午4:21
 * @since:
 */

package com.syshlang.quartz.core.common;


import java.util.Date;

/**
* @Description:    周期相关枚举类
* @Author:         sunys
* @CreateDate:     2019/3/28 19:37
* @UpdateUser:
* @UpdateDate:     2019/3/28 19:37
* @UpdateRemark:
* @Version:        1.0
*/

public enum PeriodEnum {

    WEEK_PERIOD{
        @Override
        public WEEK getPeriod(Date date) {
            int weekOfDate = DateUtil.getWeekOfDate(date);
            WEEK[] values = WEEK.values();
            return values[weekOfDate];
        }
    },
    DAY_PERIOD{
        @Override
        public DAY getPeriod(Date date) {
            int amPmOfDay = DateUtil.getAmPmOfDay(date);
            DAY[] values = DAY.values();
            return values[amPmOfDay];
        }
    };

    public enum WEEK {
       SUNDAY,
       MONDAY,
       TUESDAY,
       WEDNESDAY,
       THURSDAY,
       FRIDAY,
       SATURDAY;

    }
    public enum DAY{
        AM,
        PM;
    }

    public abstract Enum getPeriod(Date date);

}
