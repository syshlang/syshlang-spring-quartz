package com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils;


import com.syshlang.quartz.service.DynamicQuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务初始化
 * version : 1.0
 */
@Component
public class DynamicQuartzInit {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(DynamicQuartzInit.class);

    /** 定时任务service */
    @Autowired
    private DynamicQuartzService dynamicQuartzService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }

        dynamicQuartzService.initDynamicQuartz();

        if (LOG.isInfoEnabled()) {
            LOG.info("end");
        }
    }

}
