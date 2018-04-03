/**
 * Copyright &copy; 2012-2013 <a href="http://www.hzmux.com">hzmux</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hzchina.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author Song
 * @version 2014-10-01
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	public static String lowerFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toLowerCase() + str.substring(1);
		}
	}
	
	public static String upperFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 缩略字符串（替换html）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为BigDecimal类型
	 */
	public static BigDecimal toBigDecimal(Object val){
		return BigDecimal.valueOf(toDouble(val));
	}
	
	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	
	/**
	 * 转换为String类型
	 */
    public static String object2String(Object o) {
        if (null == o) {
            return "";
        } else {
            return String.valueOf(o);
        }
    }

	/**
	 * 获得i18n字符串
	 */
//	public static String getMessage(String code, Object[] args) {
//		LocaleResolver localLocaleResolver = SpringContextHolder.getBean(LocaleResolver.class);
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
//		Locale localLocale = localLocaleResolver.resolveLocale(request);
//		return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
//	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	/**
	 * 判断字符串是否在一个拼接的字符串中，如果是，返回true.
	 * 如，judgeStrInJoinString("ac","ed,ac,we",",");返回true
	 * @param desStr 要判断的字符串
	 * @param joinedStr 拼接的字符串
	 * @param separator 分隔符
	 * @return
	 */
	public static boolean judgeStrInJoinString(String desStr,String joinedStr,String separator){
		if(StringUtils.isBlank(joinedStr)){
			return false;
		}
		String[] strArr = StringUtils.split(joinedStr, separator);
		for(String str:strArr){
			if(str.equals(desStr)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 格式化标的名称
	 * 获得标的名称经指定字符分割后的字符数组
	 * 乐转No.201504270003->[0]:乐转 和[1]:No.201504270003
	 */
	public static ArrayList<String> getStringSplitList(String code) {
		String stringTmp = code;
		String splitString = "No.";
		ArrayList<String> SplitList = new ArrayList<String>();
		if(stringTmp == null || stringTmp.length() == 0){
			SplitList.add(0, "");
			SplitList.add(1, "");
		}else{
			if(stringTmp.indexOf(splitString) > 0){
				SplitList.add(0, stringTmp.split(splitString)[0]);
				SplitList.add(1, stringTmp.replaceFirst(SplitList.get(0), ""));
			}else{
				//不含分隔符时
				SplitList.add(0, "");
				SplitList.add(1, stringTmp);
			}
		}
		return SplitList;
	}
	
	/*判断该字符串是不是为数字(0~9)组成，如果是，返回true
	 识别有小数点和，正负号的数，正数返回TRUE*/
	public static boolean isNumeric(String num) {
		   try {   
			   if(Double.parseDouble(num) >= 0){
				   return true;
				 }else{		    
				    return false;
				 }
		   } catch (NumberFormatException e) {
		    return false;
		   }
		}
	
	/* 
	  * 判断是否为整数  
	  * @param str 传入的字符串  
	  * @return 是整数返回true,否则返回false  
	*/  
	  
	  public static boolean isInteger(String str) {    
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	  }  

	
}
