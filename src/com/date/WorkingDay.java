package com.date;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class WorkingDay {

	private static final String WORK_DAY = "1";				// 工作日
	private static final String HOLIDAY = "0";				// 节假日
	
	private static final String ADDRESS = "D:\\2019日历.xls";	// 地址
	
	public static void main(String[] args) throws IOException {
		Date startDate = stringToDate("2019-01-01", "yyyy-MM-dd");	// 统计开始时间
		Date endDate = stringToDate("2020-01-01", "yyyy-MM-dd");	// 统计结束时间
		List<String> dateList = Lists.newArrayList();
		Map<String, String> dateMap = Maps.newHashMap();
		List<String> holidayList = getHolidayList();
		List<String> workdayList = getWorkdayList();
		while (startDate.before(endDate)) {
			String date = dateToString(startDate, "yyyy-MM-dd");
			if (getIntegerWeek(startDate)==6 || getIntegerWeek(startDate)==7) {
				dateMap.put(date, HOLIDAY);
			} else {
				dateMap.put(date, WORK_DAY);
			}
			if (holidayList.contains(date)) {
				dateMap.put(date, HOLIDAY);
			} else if (workdayList.contains(date)) {
				dateMap.put(date, WORK_DAY);
			}
			dateList.add(date);
			startDate = getAfterDate(startDate, 1);
		}
		for (String date : dateList) {
			System.out.println(date + "-----" + dateMap.get(date));
		}
		// 创建HSSFWorkbook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建HSSFSheet对象
		HSSFSheet sheet = workbook.createSheet("sheet");
		// Excel表头
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell0 = row0.createCell(0);
		cell0.setCellValue("date");
		HSSFCell cell1 = row0.createCell(1);
		cell1.setCellValue("type:1工作日,0休息日");
		// Excel数据
		for (int i = 0; i < dateList.size(); i++) {
			HSSFRow rows = sheet.createRow(i+1);
			HSSFCell cell_0 = rows.createCell(0);
			cell_0.setCellValue(dateList.get(i));
			HSSFCell cell_1 = rows.createCell(1);
			cell_1.setCellValue(dateMap.get(dateList.get(i)).toString());
		}
		// 输出文件
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(ADDRESS);
			workbook.write(outputStream);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("导出成功");
	}
	
	/**
	 * 
	 * 方法描述:获取法定节假日
	 *
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午10:26:36
	 */
	public static List<String> getHolidayList() {
		List<String> holidayList = Lists.newArrayList();
		holidayList.add("2019-01-01");	// 元旦
		holidayList.add("2019-02-04");	// 春节
		holidayList.add("2019-02-05");
		holidayList.add("2019-02-06");
		holidayList.add("2019-02-07");
		holidayList.add("2019-02-08");
		holidayList.add("2019-02-09");
		holidayList.add("2019-02-10");
		holidayList.add("2019-04-05");	// 清明
		holidayList.add("2019-04-06");	
		holidayList.add("2019-04-07");	
		holidayList.add("2019-05-01");	// 五一
		holidayList.add("2019-05-02");
		holidayList.add("2019-05-03");
		holidayList.add("2019-06-07");	// 端午
		holidayList.add("2019-06-08");	
		holidayList.add("2019-06-09");	
		holidayList.add("2019-09-13");	// 中秋
		holidayList.add("2019-09-14");	
		holidayList.add("2019-09-15");	
		holidayList.add("2019-10-01");	// 国庆
		holidayList.add("2019-10-02");	
		holidayList.add("2019-10-03");	
		holidayList.add("2019-10-04");	
		holidayList.add("2019-10-05");	
		holidayList.add("2019-10-06");	
		holidayList.add("2019-10-07");	
		return holidayList;
	}
	
	/**
	 * 
	 * 方法描述:获取节假日调休
	 *
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午10:26:58
	 */
	public static List<String> getWorkdayList() {
		List<String> workDayList = Lists.newArrayList();
		workDayList.add("2019-02-02");	// 春节调休
		workDayList.add("2019-02-03");
		workDayList.add("2019-04-28");	// 五一调休
		workDayList.add("2019-05-05");	
		workDayList.add("2019-09-29");	// 国庆调休
		workDayList.add("2019-10-12");	
		return workDayList;
	}
	
	/**
	 * 
	 * 方法描述:获取afterDays天数之后的时间
	 *
	 * @param day
	 * @param afterDays
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午10:27:16
	 */
	public static Date getAfterDate(Date day, int afterDays) {
        Calendar afterDay = Calendar.getInstance();
        afterDay.setTime(day);
        afterDay.add(Calendar.DATE, afterDays);
		return afterDay.getTime();
	}
	
	/**
	 * 
	 * 方法描述:字符串转化成日期
	 *
	 * @param sDate
	 * @param formatPattern
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午11:01:00
	 */
	public static Date stringToDate(String sDate, String formatPattern) {
		if (sDate == null || "".equals(sDate.trim())) {
			return null;
		}
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
		try {
			date = sdf.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	 * 方法描述:日期转换字符串
	 *
	 * @param date
	 * @param formatPattern
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午11:04:12
	 */
	public static String dateToString(Date date, String formatPattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}
	
	/**
	 * 
	 * 方法描述:得到指定日期的星期数，周一为1，周日为7
	 *
	 * @param date
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年5月31日 上午11:05:02
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
