/*
 * Copyright (c) 2019.
 * @File: QuartzException.java
 * @Description:
 * @Author: sunys
 * @Date: 19-3-20 下午7:18
 * @since:
 */

package com.syshlang.quartz.core.common;



public class QuartzException extends RuntimeException {
    private String friendlyMsg;

    public String getFriendlyMsg() {
        return friendlyMsg;
    }

    public void setFriendlyMsg(String friendlyMsg) {
        this.friendlyMsg = friendlyMsg;
    }

    public QuartzException() {
    }

    public QuartzException(String message) {
        super(message);
    }

    public QuartzException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuartzException(Throwable cause) {
        super(cause);
    }

    public QuartzException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QuartzException(String message, String quartzName) {
        super(message);
        this.friendlyMsg = friendlyMsg;
    }
}
