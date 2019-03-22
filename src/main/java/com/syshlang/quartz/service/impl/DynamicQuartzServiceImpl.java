package com.syshlang.quartz.service.impl;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.build.Criteria;
import com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils.DynamicQuartzUtils;
import com.syshlang.quartz.core.quartz.schedulerfactory.quartzutils.JobUtilFactoryUtil;
import com.syshlang.quartz.model.DynamicQuartz;
import com.syshlang.quartz.model.DynamicQuartzVo;
import com.syshlang.quartz.service.DynamicQuartzService;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DynamicQuartzServiceImpl implements DynamicQuartzService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    /** 通用dao */
    @Autowired
    private JdbcDao jdbcDao;

    @Override
    public void initDynamicQuartz() {
        List<DynamicQuartz> dynamicQuartzList = jdbcDao.queryList(Criteria.select(DynamicQuartz.class));
        if (CollectionUtils.isEmpty(dynamicQuartzList)) {
            return;
        }
        for (DynamicQuartz dynamicQuartz : dynamicQuartzList) {

            CronTrigger cronTrigger = JobUtilFactoryUtil.getCronTrigger(scheduler,dynamicQuartz.getId());
            //不存在，创建一个
            if (cronTrigger == null) {
                DynamicQuartzUtils.createDynamicQuartzJob(scheduler, dynamicQuartz);
            } else {
                //已存在，那么更新相应的定时设置
                DynamicQuartzUtils.updateDynamicQuartzJob(scheduler, dynamicQuartz);
            }
        }
    }

    @Override
    public List<DynamicQuartzVo> getDynamicQuartzList(DynamicQuartzVo dynamicQuartzVo) {
        List<DynamicQuartz> dynamicQuartzList = jdbcDao.queryList(dynamicQuartzVo.getTargetObject(DynamicQuartz.class));
        List<DynamicQuartzVo> dynamicQuartzVoList = BeanConverter.convert(DynamicQuartzVo.class, dynamicQuartzList);
        return dynamicQuartzVoList;
    }

    @Override
    public Long doSaveDynamicQuartz(DynamicQuartzVo dynamicQuartzVo) {
        DynamicQuartz dynamicQuartz = dynamicQuartzVo.getTargetObject(DynamicQuartz.class);
        DynamicQuartzUtils.createDynamicQuartzJob(scheduler, dynamicQuartz);
        return jdbcDao.insert(dynamicQuartz);
    }

    @Override
    public String doPauseOrResume(Long id) {
        return null;
    }

    @Override
    public String doRunAtOnce(Long id) {
        return null;
    }
}
