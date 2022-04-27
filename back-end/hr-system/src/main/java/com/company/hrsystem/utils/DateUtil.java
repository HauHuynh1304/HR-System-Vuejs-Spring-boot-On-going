package com.company.hrsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static final String DAY_HOUR_SECOND = "yyyy/MM/dd HH:mm:ss";

	public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";
;
	public static String getCurrentDayHourSecond() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DAY_HOUR_SECOND);
		LocalDateTime time = LocalDateTime.now();
		return time.format(format);
	}
	
}
