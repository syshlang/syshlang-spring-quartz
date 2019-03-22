/*
 * Copyright (c) 2019. GRGBanking
 * @File: MessageSourceHelper.java
 * @Description:
 * @Author: sunys
 * @Date: 19-2-19 下午4:21
 * @since:
 */

package com.syshlang.quartz.core.common;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;


/**
 * 读取语言配置工具
 * @author
 *
 */
public class MessageSourceHelper {
	
	private static ResourceBundleMessageSource messageSource;
	
	public static void setMessageSource(ResourceBundleMessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}
	
	/**
     * 根据当前会话设置语言地区判断选择语言，如果会话没有设置，则根据当前所在地区决定语言
     * @param key 配置文件中的key
     * @param arguments 对应取出的语句中占位符的替换参数
     * @return
     */
    public static String getMessage(String key, Object... arguments ) {
		Locale locale = null;//(Locale)UserContext.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(locale == null) {
			locale = Locale.SIMPLIFIED_CHINESE;
		}
		return getMessage(key, arguments, locale);
    }
	
	public static String getMessage(String key, Object[] arguments, Locale locale) {
		return messageSource.getMessage(key, arguments, locale);
	}

}
