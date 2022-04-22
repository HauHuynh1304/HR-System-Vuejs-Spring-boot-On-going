package com.company.hrsystem.utils;

import org.springframework.stereotype.Service;

@Service
public class StringUtil {

	public Boolean validEmail(String email) {
		return true;
	}
	
	public static String apiBuilder(String... string) {
		StringBuilder result = new StringBuilder();
		for (String value : string) {
			result.append(value);
		}
		return result.toString();
	}

}