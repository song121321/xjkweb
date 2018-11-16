package com.empl.mgr.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.empl.mgr.constant.FormatConstant;

public class DateTimeUtil {

	/*
	 * 获取当前时间 te5l.com [K]
	 */
	public static Date getCurrentTime() {
		return new Date();
	}

	/*
	 * 转换时间 te5l.com [K]
	 */
	public static String conversionTime(Date date, String format) {
		if (CompareUtil.isEmpty(date) || StringUtils.isEmpty(format))
			return "";
		return new SimpleDateFormat(format).format(date);
	}

	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(FormatConstant.DATETIMEFORMAT);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

}
