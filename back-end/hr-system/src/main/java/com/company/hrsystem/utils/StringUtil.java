package com.company.hrsystem.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class StringUtil {

	public static final String EMAIL_PATTERN = "^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*\\@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*";
	
	public static final String NUMBER_PATTERN = "^[0-9]+";
	
	public static final String SPACE_PATTERN = "\\s+";

	public static Boolean validEmail(String email) {
		return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
	}
	
	public static Boolean validNumber(String number) {
		return Pattern.compile(NUMBER_PATTERN).matcher(number).matches();
	}

	public static String apiBuilder(String... string) {
		StringBuilder result = new StringBuilder();
		for (String value : string) {
			result.append(value);
		}
		return result.toString();
	}
	
	public static String trimAllSpace(String string) {
		return string.replaceAll(SPACE_PATTERN, " ").trim();
	}

}