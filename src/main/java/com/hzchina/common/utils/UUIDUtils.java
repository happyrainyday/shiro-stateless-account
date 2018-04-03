package com.hzchina.common.utils;

import java.util.Date;
import java.util.UUID;

public class UUIDUtils {

	private static String TC = "TC";
	private static String FX = "KQFX";
	private static String JX = "KQJX";

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	public static String getTid(int templetType) {
		String ti = null;

		if (templetType == 0) {
			ti = JX + DateUtil.formatDate2(new Date());
		}
		if (templetType == 1) {
			ti = FX + DateUtil.formatDate2(new Date());
		}
		return ti;
	}

	public static String getPid() {
		String pid = TC + DateUtil.formatDate2(new Date());
		return pid;

	}
}
