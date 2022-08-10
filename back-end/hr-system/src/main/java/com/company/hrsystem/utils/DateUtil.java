package com.company.hrsystem.utils;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import com.company.hrsystem.enums.PartialDateEnum;

public class DateUtil {

	public static final String DAY_HOUR_SECOND = "yyyy-MM-dd HH:mm:ss";

	public static final String DAY = "yyyy/MM/dd";

	public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";;

	public static Timestamp getCurrentDayHourSecond() {
		LocalDateTime time = LocalDateTime.now();
		return Timestamp.valueOf(time);
	}

	public static Double caculateDuration(String type, Timestamp startDate, Timestamp endDate)
			throws DateTimeException {
		double ratio = 0;
		double duration = 0;
		if (type.equals(PartialDateEnum.ALL_DAY.getValue()) || type.equals(PartialDateEnum.HOURS.getValue())) {
			ratio = 1;
		} else if (type.equals(PartialDateEnum.HALF_DAY.getValue())) {
			ratio = 0.5;
		}

		if (type.equals(PartialDateEnum.HOURS.getValue())) {
			duration = (int) (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
		} else {
			duration = (int) (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;
		}
		return ratio * duration;
	}

	public static boolean isPreviousMonthYear(Timestamp date) {
		LocalDateTime coverDate = date.toLocalDateTime();
		LocalDateTime currentDate = LocalDateTime.now();
		if (coverDate.getMonthValue() < currentDate.getMonthValue() || coverDate.getYear() < currentDate.getYear()) {
			return true;
		}
		return false;
	}
}
