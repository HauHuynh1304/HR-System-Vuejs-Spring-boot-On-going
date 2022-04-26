package com.company.hrsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static final String DayHourSecond = "yyyy/MM/dd HH:mm:ss";

	public static String getCurrentDayHourSecond() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DayHourSecond);
		LocalDateTime time = LocalDateTime.now();
		return time.format(format);
	}
	
}
