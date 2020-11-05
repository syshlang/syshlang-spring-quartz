/*
 * Copyright (c) 2019.
 * @File: SysJobLog.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-22 下午2:08
 * @since:
 */

package com.syshlang.quartz.model;


import lombok.Data;

import java.util.Date;

/**
 * 调度日志表
 * 
 * @author
 */
@Data
public class DynamicQuartzLog {


    /** ID */
    private Long id;

    /**任务详情***/
    private String DynamicQuartzDetails;

    /** 日志信息 */
    private String message;

    /** 执行状态（0正常 1失败） */
    private String execStatus;

    /** 异常信息 */
    private String exceptionInfo;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDynamicQuartzDetails() {
        return DynamicQuartzDetails;
    }

    public void setDynamicQuartzDetails(String dynamicQuartzDetails) {
        DynamicQuartzDetails = dynamicQuartzDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
