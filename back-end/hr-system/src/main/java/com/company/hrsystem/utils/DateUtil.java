package com.company.hrsystem.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.company.hrsystem.enums.PartialDateEnum;

public class DateUtil {

	public static final String DAY_HOUR_SECOND = "yyyy/MM/dd HH:mm:ss";

	public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";;

	public static String getCurrentDayHourSecond() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DAY_HOUR_SECOND);
		LocalDateTime time = LocalDateTime.now();
		return time.format(format);
	}

	public static Double caculateDuration(String type, String startDate, String endDate) throws DateTimeException {
		double ratio = 0;
		if (type.equals(PartialDateEnum.ALL_DAY.getValue())) {
			ratio = 1;
		} else if (type.equals(PartialDateEnum.HALF_DAY.getValue())) {
			ratio = 0.5;
		}
		Period diff = Period.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
		double duration = diff.getDays() + 1;
		return ratio * duration;
	}

	public static boolean isPreviousMonth(String date) {
		LocalDate coverDate = LocalDate.parse(date);
		LocalDate currentDate = LocalDate.now();
		if (coverDate.getMonthValue() < currentDate.getMonthValue() || coverDate.getYear() < currentDate.getYear()) {
			return true;
		}
		return false;
	}
}
