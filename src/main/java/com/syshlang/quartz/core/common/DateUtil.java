/*
 * Copyright (c) 2019. GRGBanking
 * @File: DateUtil.java
 * @Description:
 * @Author: sunys
 * @Date: 19-2-19 下午4:21
 * @since:
 */

package com.syshlang.quartz.core.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 日期格式转换，处理工具
 */
public class DateUtil {

    public static final String FORMAT_1 = "yyyy";
    public static final String FORMAT_2 = "yyyy-MM";
    public static final String FORMAT_3 = "yyyy-MM-dd";
    public static final String FORMAT_4 = "yyyy-MM-dd HH";
    public static final String FORMAT_5 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_6 = "yyyy-MM-dd HH:mm:ss";

    private static final int DAYSECOND = 24 * 3600 * 1000;
    private static final int MONTHLENGTH = 4;
    
    /**
     * 将日期类型对象转换成yyyy-MM-dd类型字符串 如果传入的日期为null,则返回空值
     * 
     * @param date
     *            日期类型对象
     * @return 日期格式字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(date);
    }

    /**
     * 按照指定的格式，将日期类型对象转换成字符串，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss
     * 如果传入的日期为null,则返回空值
     * 
     * @param date
     *            日期类型对象
     * @param format
     *            需转换的格式
     * @return 日期格式字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * 将日期类型对象转换成yyyy-MM-dd HH:mm:ss类型字符串 如果传入的日期为null,则返回空值
     * 
     * @param date
     *            日期类型对象
     * @return 日期格式字符串
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(date);
    }

    /**
     * 将字符串（yyyy-MM-dd）解析成日期
     * 
     * @param dateStr
     *            日期格式的字符串
     * @return 日期类型对象
     */
    public static Date parseDate(String dateStr) {
        String dateStrNew = "";
        if (dateStr.indexOf("/") != -1) {
            dateStrNew = dateStr.replaceAll("/", "-");
        }
        return parseDate(dateStrNew, "yyyy-MM-dd");
    }

    /**
     * 按照指定的格式，将字符串解析成日期类型对象，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss
     * 
     * @param dateStr
     *            日期格式的字符串
     * @param format
     *            字符串的格式
     * @return 日期类型对象
     */
    public static Date parseDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串解析成对应日期格式的日期
     * 
     * @param value
     *            日期格式字符串
     * @return 日期类型对象
     */
    public static Date parse(String value) {
        String valueNew = "";
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        valueNew = value.trim().replaceAll("/", "-");
        if (valueNew.length() == FORMAT_1.length()) {
            return parseDate(valueNew, FORMAT_1);
        } else if (valueNew.length() == FORMAT_2.length()) {
            return parseDate(valueNew, FORMAT_2);
        } else if (valueNew.length() == FORMAT_3.length()) {
            return parseDate(valueNew, FORMAT_3);
        } else if (valueNew.length() == FORMAT_4.length()) {
            return parseDate(valueNew, FORMAT_4);
        } else if (valueNew.length() == FORMAT_5.length()) {
            return parseDate(valueNew, FORMAT_5);
        } else if (valueNew.length() == FORMAT_6.length()) {
            return parseDate(valueNew, FORMAT_6);
        } else {
            throw new RuntimeException("解析日期格式出错，与指定格式不匹配.");
        }
    }

    /**
     * 两个时间相差的天数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        // 先将时分秒都去掉
        Date start = DateUtil.parseDate(DateUtil.formatDate(startDate, "yyyy-MM-dd"));
        Date end = DateUtil.parseDate(DateUtil.formatDate(endDate, "yyyy-MM-dd"));
        Long intervalDays = (end.getTime() - start.getTime()) / DAYSECOND;
        return intervalDays.intValue();
    }

    // ------------returns value as Date
    /**
     * 日期格式 示例 yyyy-MM-dd HH-mm-ss 2016-02-02 02:02:02, 2016/2/2 2:2:2, 2016年2月2日
     * 2时2分2秒 yyyy-MM-dd HH-mm 2016-02-02 02:02 yyyy-MM-dd HH 2016-02-02 02
     * yyyy-MM-dd 2016-02-02, 2016年02月02日 yyyy-MM 2016-02 yyyy 2016 yyyyMMddHHmmss
     * 20160202020202 yyyyMMddHHmm 201602020202 yyyyMMddHH 2016020202 yyyyMMdd
     * 20160202 yyyyMM 201602 HH:mm:ss 02:02:02 HH:mm 02:02 yy-MM-dd 02.02.02 dd-MM
     * 02.02 dd-MM-yyyy 02.02.2016 java.text.DateFormat 07/10/96 4:5 PM, PDT
     * 
     * @param key
     * @return
     */
    public Date getDate(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return getDateByValue(key);
    }

    /**
     * Returns the value of java.sql.Date type to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * 
     * @param key
     *            key with which the specified value is to be associated.
     * 
     * @return java.sql.Date value to which the specified key is mapped, or null if
     *         this map contains no mapping for the key.
     */
    public java.sql.Date getDateForSql(String key) {
        Date date = getDate(key);
        if (date == null) {
        	return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * Adds a pattern.
     * 
     * @param pattern
     *            regular expression.
     * @param date
     *            format.
     */
    public static void addDateFormat(String pattern, String format) {
        dateParsers.add(new DateParser(pattern, format));
    }

    /**
     * Converts the specified Object to a String.
     * 
     * @param value
     *            the Object to be converted.
     * @return String value.
     */
    protected String getStringValue(Object value) {
        if (value == null) {
            return null;
        }else if (value instanceof String) {
            return (String) value;
        }else if (value.getClass().isArray()) {
            if (value instanceof int[]) {
                return Arrays.toString((int[]) value);
            } else if (value instanceof boolean[]) {
                return Arrays.toString((boolean[]) value);
            } else if (value instanceof byte[]) {
                return Arrays.toString((byte[]) value);
            } else if (value instanceof char[]) {
                return Arrays.toString((char[]) value);
            } else if (value instanceof double[]) {
                return Arrays.toString((double[]) value);
            } else if (value instanceof float[]) {
                return Arrays.toString((float[]) value);
            } else if (value instanceof long[]) {
                return Arrays.toString((long[]) value);
            } else if (value instanceof short[]) {
                return Arrays.toString((short[]) value);
            } else {
                Object[] ss = (Object[]) value;
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < ss.length; i++) {
                    builder.append(getStringValue(ss[i]));
                    if (i != ss.length - 1) {
                        builder.append(',').append(' ');
                    }
                }
                return builder.insert(0, '[').append(']').toString();
            }
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Converts the given object to a date.
     * 
     * @param value
     *            Object to be converted.
     * @return Date.
     */
    protected Date getDateByValue(Object value) {
        if (value == null) {
            return null;
        }else if (value instanceof Date) {
            return (Date) value;
        }else {
            String s = getStringValue(value);
            for (DateParser dateParser : dateParsers) {
                Date date = dateParser.parse(s);
                if (date != null) {
                    return date;
                }
            }

            try {
                Date d = DateFormat.getInstance().parse(s);
                if (d != null) {
                    return d;
                }
            } catch (ParseException e) {
                // ignore
            }

            throw new ClassCastException("Couldn't convert " + s + "(" + value.getClass().getName()
                    + ") to date whitch toString is " + s + ", no date format parttern matches.");
        }
    }

    /**
     * Date Parser
     */
    static class DateParser {
        private String regular;
        private Pattern pattern;
        private SimpleDateFormat sdf;

        public DateParser(String pattern, String format) {
            super();
            this.regular = pattern;
            this.pattern = Pattern.compile(pattern);
            this.sdf = new SimpleDateFormat(format);
        }

        /**
         * Converts the given string to a date if this string matches one of the
         * built-in patterns.
         * 
         * @param dateStr
         *            string to be converted.
         * @return date, or null
         */
        public Date parse(String dateStr) {
            if (pattern.matcher(dateStr).matches()) {
                String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String dateStrNew = "";
                if (regular.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                        || regular.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {
                    dateStrNew = curDate + "-" + dateStr;
                } else if (regular.equals("^\\d{1,2}\\D+\\d{1,2}$")) {
                    dateStrNew = curDate.substring(0, MONTHLENGTH) + "-" + dateStr;
                }
                String dateReplace = dateStrNew.replaceAll("\\D+", "-");
                try {
                    return sdf.parse(dateReplace);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(
                            "Couldn't parse string '" + dateStr + "' to date which matches pattern '"
                                    + pattern.pattern() + "' and format '" + sdf.toPattern() + "', " + e.getMessage());
                }
            }
            return null;
        }
    }

    private static volatile List<DateParser> dateParsers = new ArrayList<DateParser>() {
        private static final long serialVersionUID = -1336942317939192127L;

        {
            // 2016-02-02 02:02:02, 2016/2/2 2:2:2, 2016年2月2日 2时2分2秒
            add(new DateParser("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$",
                    "yyyy-MM-dd-HH-mm-ss"));
            // 2016-02-02 02:02
            add(new DateParser("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yyyy-MM-dd-HH-mm"));
            add(new DateParser("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yyyy-MM-dd-HH"));// 2016-02-02 02
            add(new DateParser("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$", "yyyy-MM-dd"));// 2016-02-02, 2016年02月02日
            add(new DateParser("^\\d{4}\\D+\\d{1,2}\\D*$", "yyyy-MM"));// 2016-02
            add(new DateParser("^\\d{4}\\D*$", "yyyy"));// 2016
            add(new DateParser("^\\d{14}$", "yyyyMMddHHmmss"));// 20160202020202
            add(new DateParser("^\\d{12}$", "yyyyMMddHHmm"));// 201602020202
            add(new DateParser("^\\d{10}$", "yyyyMMddHH"));// 2016020202
            add(new DateParser("^\\d{8}$", "yyyyMMdd"));// 20160202
            add(new DateParser("^\\d{6}$", "yyyyMM"));// 201602
            add(new DateParser("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm-ss"));// 02:02:02
            add(new DateParser("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm"));// 02:02
            add(new DateParser("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd"));// 02.02.02
            add(new DateParser("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM"));// 02.02
            add(new DateParser("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy"));// 02.02.2016
        }
    };

    /**
	 * 对指定的日期增加或减少指定的天数
	 * 
	 * @param date
	 *            需要修改的日期对象
	 * @param amount
	 *            需要修改的数量，如果需要增加一天，amount=1,如果减少一天，amount=-1;
	 * @return 修改后日期类型对象
	 */
	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

    /**
     * 根据日期获取 星期
     * @param date
     * @return 0周日 1周一 2周二 3周三 4周四 5周五 6周六
     */
    public static int getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return w;
    }

    /**
     * 根据时间判断上下午
     * @param date
     * @return 0上午 1下午
     */
    public static int getAmPmOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.AM_PM);
        return w;
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        Enum period = PeriodEnum.WEEK_PERIOD.getPeriod(new Date());
        System.out.println(period);
        Enum period1 = PeriodEnum.DAY_PERIOD.getPeriod(new Date());
        System.out.println(period1);
    }
}
