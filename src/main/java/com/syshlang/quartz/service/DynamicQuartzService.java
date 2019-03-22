package com.syshlang.quartz.service;

import com.syshlang.quartz.model.DynamicQuartzVo;

import java.util.List;

/**
 * The interface Dynamic quartz service.
 */
public interface DynamicQuartzService {


    /**
     * Init dynamic quartz.
     */
    void initDynamicQuartz();

    /**
     * Gets quartz job json list.
     *
     * @param dynamicQuartzVo the dynamic quartz vo
     * @return the quartz job json list
     */
    List<DynamicQuartzVo> getDynamicQuartzList(DynamicQuartzVo dynamicQuartzVo);

    /**
     * Do save dynamic quartz string.
     *
     * @param dynamicQuartzVo the dynamic quartz vo
     * @return the string
     */
    Long doSaveDynamicQuartz(DynamicQuartzVo dynamicQuartzVo);


    /**
     * Do pause or resume string.
     *
     * @param id the id
     * @return the string
     */
    String doPauseOrResume(Long id);


    /**
     * Do run at once string.
     *
     * @param id the id
     * @return the string
     */
    String doRunAtOnce(Long id);
}
