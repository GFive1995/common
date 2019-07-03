package com.util;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 获取工作日和节假日后导入Excel
 * 
 * @version 1.0
 */
public class WorkingDay {

	private static final String WORK_DAY = "1";					// 工作日
	private static final String HOLIDAY = "0";					// 节假日
	
	private static final String PATH = "D:\\2019工作日节假日.xlsx";	// 地址
	private static List<String> HEADLIST = Arrays.asList("date", "type");
	
	public static void main(String[] args) throws IOException {
		Date startDate = DateUtil.stringToDate("2019-01-01", "yyyy-MM-dd");	// 统计开始时间
		Date endDate = DateUtil.stringToDate("2020-01-01", "yyyy-MM-dd");	// 统计结束时间
		List<Map<String, Object>> dataList = Lists.newArrayList();
		List<String> dateList = Lists.newArrayList();
		List<String> holidayList = getHolidayList();
		List<String> workdayList = getWorkdayList();
		while (startDate.before(endDate)) {
			String type = WORK_DAY;
			String date = DateUtil.dateToString(startDate, "yyyy-MM-dd");
			if (DateUtil.getIntegerWeek(startDate)==6 || DateUtil.getIntegerWeek(startDate)==7) {
				type = HOLIDAY;
			}
			if (holidayList.contains(date)) {
				type = HOLIDAY;
			} else if (workdayList.contains(date)) {
				type = WORK_DAY;
			}
			dateList.add(date);
			startDate = DateUtil.getAfterDate(startDate, 1);
			Map<String, Object> paraMap = Maps.newHashMap();
			paraMap.put("date", date);
			paraMap.put("type", type);
			dataList.add(paraMap);
		}
		ExcelUtil.downloadExcel("2019工作日节假日.xlsx", PATH, HEADLIST, HEADLIST, dataList);
		System.out.println("导出成功");
	}
	
	/**
	 * 
	 * 方法描述:获取法定节假日
	 *
	 * @return
	 * 
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
	
}
