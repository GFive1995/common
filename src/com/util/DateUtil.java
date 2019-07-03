package com.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String FORMAT_SERIAL_NUMBER = "yyyyMMddHHmmss";
	public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public static void main(String[] args) {
		System.out.println(dateToString(new Date(), DateUtil.FORMAT_DEFAULT));
		System.out.println(stringToDate("2019-07-03 00:00:00", DateUtil.FORMAT_DEFAULT));
		System.out.println(getAfterDate(new Date(), 1));
		System.out.println(getBeforeDate(new Date(), 1));
		System.out.println(getIntegerWeek(new Date()));
	}

	/**
	 * 
	 * 方法描述:日期转换字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 * 
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 
	 * 方法描述:字符串转换日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 * 
	 */
	public static Date stringToDate(String date, String pattern) {
		if (date==null || "".equals(date.trim())) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 方法描述:获取某一日期后指定天数的日志
	 *
	 * @param date
	 * @param day
	 * @return
	 * 
	 */
	public static Date getAfterDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 方法描述:获取某一日期前指定天数的日志
	 *
	 * @param date
	 * @param day
	 * @return
	 * 
	 */
	public static Date getBeforeDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0 - day);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 方法描述:得到指定日期的星期数，周一为1，周日为7
	 *
	 * @param date
	 * @return
	 * 
	 */
	public static Integer getIntegerWeek(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int week = cd.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			week = 7;
		} else {
			week = week - 1;
		}
		return week;
	}
}
