package com.hzchina.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具
 */
public class DateUtils {

	public static final int MILLINS_OF_DAY = 86400000;

	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_CH = "yyyy年MM月dd日";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYYMMDD = "yyyy/MM/dd";
	public static final String YYYYMMDD_NO_SEPARATE = "yyyyMMdd";
	public static final String YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	private DateUtils() {
		super();
	}

	/*
	 * 日期转字符串YYYY_MM_DD
	 */
	public static String formatDate(Date dateString) {
		String format = YYYY_MM_DD;
		if (dateString == null || StringUtils.isBlank(format)) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(dateString);
	}

	/**
	 * 日期转字符串 日期格式默认yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToString(Date date, String pattern) {
		final SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern);
		String formatResult = sdf.format(date);
		return formatResult;
	}

	/**
	 * 字符串转日期:默认格式yyyy-MM-dd
	 */
	public static Date stringToDateYyyyMMdd(String dateString) {

		return stringToDate(dateString, YYYY_MM_DD);
	}

	/**
	 * 字符串转日期:默认格式yyyy-MM-dd HH:mm
	 */
	public static Date stringToDateYyyyMMddhhmm(String dateString) throws ParseException {
		return stringToDate(dateString, YYYY_MM_DD_HH_MM);

	}

	/**
	 * 字符串转日期:默认格式yyyy-MM-dd HH:mm:ss
	 */
	public static Date stringToDateYyyyMMddhhmmss(String dateString) {
		return stringToDate(dateString, YYYY_MM_DD_HH_MM_SS);

	}

	/**
	 * 字符串转日期
	 */
	public static Date stringToDate(String dateString, String pattern) {
		Date date = null;
		try {
			date = org.apache.commons.lang3.time.DateUtils.parseDate(dateString,
					StringUtils.isBlank(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串日期格式修改
	 */
	public static String changePattern(String dateString, String pattern) throws ParseException {
		Date stringToDateYyyyMMddhhmmss = stringToDateYyyyMMddhhmmss(
				StringUtils.isBlank(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern);
		return dateToString(stringToDateYyyyMMddhhmmss, pattern);
	}

	/**
	 * 在日期上增加数个分钟
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMinute(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, n);
		return cal.getTime();
	}

	/**
	 * 格式化时间 add by 滕景山
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null || StringUtils.isBlank(format)) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(date);
	}

	/**
	 * 在日期上增加数个天
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 
	 * 添加或减少月 add by wzx 比如当前日期为“2015-05-29”，使用此方法往前推迟三个月是“2015-02-28”
	 * 比如当前日期为“2015-11-24”，使用此方法往前推迟两个月是“2015-09-23”
	 * 
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
	 * 判断字符串是否是日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年-两位月份-两位日期，注意yyyy-MM-dd区分大小写
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static boolean isValidTimestamp(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年-两位月份-两位日期，注意YYYY_MM_DD_HH_MM_SS区分大小写
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 两个日期之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetweenTwoDate(String date1, String date2) {
		Date oneDate = stringToDate(date1, DateUtils.YYYY_MM_DD);
		Date twoDate = stringToDate(date2, DateUtils.YYYY_MM_DD);
		long between_days =0;
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(oneDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(twoDate);
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	/**
	 * 两个日期之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int timesBetweenTwoDate(String date1, String date2) {
		Date oneDate = stringToDate(date1, DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date twoDate = stringToDate(date2, DateUtils.YYYY_MM_DD_HH_MM_SS);
		long between_days =0;
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(oneDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(twoDate);
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	/**
	 * 计算两个时间之间相差的秒数，“结束时间”减去“开始时间”之间的秒数.
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return int 秒数。如果开始时间或者结束时间为 null 的话，返回 0 。
	 */
	public static int secondsBetween(Date beginTime, Date endTime) {
		if (beginTime == null || endTime == null) {
			return 0;
		}
		long diff = endTime.getTime() - beginTime.getTime();
		return (int) diff / 1000;
	}

	/**
	 * 当月最后一天的时间 带时分秒(2014-12-31 23:59:59)
	 * 
	 * @return
	 */
	public static Date getEndOfTheMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 两个日期相差月数
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
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
	 * 当月第一天的时间 带时分秒(2014-12-01 00:00:00)
	 * 
	 * @return
	 */
	public static Date getBeginOfTheMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		return c.getTime();
	}
}
