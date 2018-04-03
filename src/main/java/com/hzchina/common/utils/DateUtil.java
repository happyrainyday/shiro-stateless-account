package com.hzchina.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 日期处理静态类
 * 
 * @author xulijun
 * 
 */
public class DateUtil {

	final static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
    public static final String TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTON_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_PATTON_DEFAULT_CH = "yyyy年MM月dd日";
    public static final String DATA_PATTON_YYYYMMDD = "yyyyMMdd";
    public static final String TIME_PATTON_HHMMSS = "HH:mm:ss";
    public static final String TIME_PATTON_HHMM = "HH:mm";
    public static final String DATE_PATTON_YYYY_MM = "yyyy-MM";

    public static final long DURATION = 1000L * 60 * 60 * 24;// 一天

    public static final long DURATION_HOUR = 1000L * 60 * 60;// 一小时

    /**
     * 计算两个时间之间相差的小时数，“结束时间”减去“开始时间”之间的分钟数.
     * 
     * @param compareTime
     *            比较时间
     * @param currentTime
     *            当前时间
     * @return int 小时数。如果开始时间或者结束时间为 null 的话，返回 0 。
     */
    public static int hoursBetween(Date compareTime, Date currentTime) {
        if (compareTime == null || currentTime == null)
            return 0;
        return (int) ((compareTime.getTime() - currentTime.getTime()) / DURATION_HOUR);
    }

    /**
     * 计算两个时间之间相差的分钟数，“结束时间”减去“开始时间”之间的分钟数.
     * 
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return int 分钟数。如果开始时间或者结束时间为 null 的话，返回 0 。
     */
    public static int minutesBetween(Date beginTime, Date endTime) {
        if (beginTime == null || endTime == null) {
            return 0;
        }
        long diff = endTime.getTime() - beginTime.getTime();
        return (int) diff / (60 * 1000);
    }

    /**
     * 两个日期之间的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetweenTwoDate(Date date1, Date date2) {
    	date1 = DateUtil.parseDate(DateUtil.DATE_PATTON_DEFAULT, DateUtil.formatDate(date1, DateUtil.DATE_PATTON_DEFAULT));
    	date2 = DateUtil.parseDate(DateUtil.DATE_PATTON_DEFAULT, DateUtil.formatDate(date2, DateUtil.DATE_PATTON_DEFAULT));
    	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取相对于某个日期几天后的日期
     * 
     * @param date
     *            --开始日期
     * @param days
     *            --天数
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * 获取当前时间
     * 
     * @return DATE<br>
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return currDate;
    }

    /**
     * 获取当前时间指定样式的时间字符串
     */
    public static String getCurrentDateByFormat(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    /**
     * 根据日期字符串返回日期类型数据 格式为 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateTime(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_PATTON_DEFAULT);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    
   
    /**
     * 根据日期字符串返回日期类型数据 格式为 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTON_DEFAULT);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    /**
     * 时间字符串 格式化成 指定格式时间字符串
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static String formatDate(String dateString, String format) {
        if (StringUtils.isBlank(dateString) || StringUtils.isBlank(format)) {
            return "";
        }
        Date date = parseDate(StringUtils.isBlank(format) ? TIME_PATTON_DEFAULT
                : format, dateString);
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * 时间 格式化成 指定格式时间字符串
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static String formatDate(Date dateString, String format) {
        if (dateString == null || StringUtils.isBlank(format)) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(dateString);
    }
    /**
     * 时间 格式化成 指定格式时间字符串
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static String formatDate2(Date dateString) {
    	String format=DATA_PATTON_YYYYMMDD;
        if (dateString == null || StringUtils.isBlank(format)) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(dateString);
    }
    public static Date parseDate(String strFormat, String dateValue) {
        if (dateValue == null)
            return null;

        if (strFormat == null)
            strFormat = TIME_PATTON_DEFAULT;

        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }

        return newDate;
    }

    public static String nextMonthDay(Date date, int n) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.add(Calendar.MONTH, n);
        // System.out.println(today.get(Calendar.YEAR) + "年" +
        // (today.get(Calendar.MONTH)+1)+ "月" +today.get(Calendar.DAY_OF_MONTH)+
        // "日");
        return today.get(Calendar.YEAR) + "年" + (today.get(Calendar.MONTH) + 1)
                + "月" + (today.get(Calendar.DAY_OF_MONTH) - 1) + "日";

    }
    
    public static String getYearMonthDayStr(Date date) {
    	if(date == null){
    		return "";
    	}
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        return today.get(Calendar.YEAR) + "年" + (today.get(Calendar.MONTH) + 1)
                + "月" + (today.get(Calendar.DAY_OF_MONTH)) + "日";

    }

    /**
     * 得到距当天n天的凌晨 比如昨天 n=-1 今天 n=0 明天 n=1
     * 
     * @return
     * @throws Exception
     */
    public static Date getDayStart(int n) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String towDaysBefore = sdf.format(calendar.getTime()) + " 00:00:00";
        System.out.println(towDaysBefore);
        return (Date) sdf.parse(towDaysBefore);
    }

    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static Long countMillisecondAfterNow( Date toDate){
        if(null == toDate )  toDate = new Date();
        Date from = new Date();
        long fromL = from.getTime();
        long toL = toDate.getTime();
        return toL - fromL;
    }
    
    /**
     * 开始投标时间 已经开始投标则为0
     * @param toDate
     * @return
     */
    public static Long secondAfterNowPositive(Date toDate){
        if(null == toDate){
        	return 0L;
        }
        Date from = new Date();
        long fromL = from.getTime();
        long toL = toDate.getTime();
        if(toL - fromL >= 0){
        	return toL - fromL;
        }else{
        	return 0L;
        }
    }
    
    /**
     * 当月第一天的时间 带时分秒(2014-12-01 00:00:00)
     * @return
     */
    public static Date getBeginOfTheMonth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE,c.getActualMinimum(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }
    
    /**
     * 当月最后一天的时间 带时分秒(2014-12-31 23:59:59)
     * @return
     */
    public static Date getEndOfTheMonth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 判断日期是不是今天
     * @param date
     * @return
     */
    public static boolean isToday(Date date){
        if (null == date) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (sdf.format(new Date()).equals(sdf.format(date))) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断日期是不是昨天
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date){
        if (null == date) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        if (sdf.format(c.getTime()).equals(sdf.format(date))) {
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * 明年最后一天的时间 带时分秒(2014-12-31 23:59:59)
     * @return
     */
    public static Date getNextYearLastDay(){
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR)+1, 11, 31, 23, 59, 59);
        return c.getTime();
    }

    /**
     * 获取与现在差num个月的月初时间
     * @param num
     * @return
     */
    public static Date getdefinedMonthFirstDay(int num){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, num);
        c.set(Calendar.DATE,c.getActualMinimum(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }
    
    /**
     * 计算剩余天数
     * @param serial
     * @return
     */
    public static Long remainderTermZhaiquan(Date serial){
    	Date date1 = DateUtil.parseDate(DateUtil.DATE_PATTON_DEFAULT, DateUtil.formatDate(serial, DateUtil.DATE_PATTON_DEFAULT));
    	Date date2 = DateUtil.parseDate(DateUtil.DATE_PATTON_DEFAULT, DateUtil.formatDate(new Date(), DateUtil.DATE_PATTON_DEFAULT));
        long day = (date1.getTime() - date2.getTime())/ (24 * 60 * 60 * 1000);
        if(day < 0){
        	return 0L;
        }
        return day; 
    }  
    
    /**
     * 计算剩余天数--e取结算用
     * @param serial
     * @return
     */
    public static Long equRemainderTerm(Date startDate, Date endDate){
        long day = (endDate.getTime() - startDate.getTime())/ (24 * 60 * 60 * 1000);
        return day; 
    } 
    
    /**
     * 返回指定日期是几点
     * 
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 返回指定日期的分钟
     * add by 滕景山
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 
     * 添加或减少月
     * add by 滕景山
     * 比如当前日期为“2015-05-29”，使用此方法往前推迟三个月是“2015-02-28”
     * 比如当前日期为“2015-11-24”，使用此方法往前推迟两个月是“2015-09-23”
     * add by 滕景山
     * @param date
     * @param month
     * @return
     */
    public static Date addMonthFroDate(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
    
    /**
     * 返回指定日期是哪一年
     * add by 滕景山
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }
    
    /**
     * 返回指定日期月份（0代表一月，1代表二月...）
     * add by 滕景山
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }
    
    /**
     * 返回指定日期是哪一天
     * add by 滕景山
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }
    
    /**
	 * 两个日期相差月数
	 * @param date1 <String>
	 * @param date2 <String>
	 * @return int
	 */
	public static int getMonthSpace(Date start, Date end) {
		try {
			int result = 0;
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(start);
			c2.setTime(end);
			result = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH)
					- c1.get(Calendar.MONTH);
			return result;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
     * 格式化日期至指定格式，返回Date类型
     * add by 滕景山
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDate(String dateStr, String format) {

        if (dateStr == null || format == null) {
            log.debug("数据类型异常" + dateStr + "|" + format);
        }

        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(dateStr);
        } catch (Exception ex) {
            return null;
        }
    }
}
