package com.company.hrsystem.commons.utils;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> objectToMap(Object object) {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}

	public static int countNotNullParamater(Object object) {
		Map<String, Object> objToMap = objectToMap(object);
		int countNotNullParamater = 0;
		for (Map.Entry<String, Object> entry : objToMap.entrySet()) {
			if (ObjectUtils.isNotEmpty(entry.getValue())) {
				countNotNullParamater++;
			}
		}
		return countNotNullParamater;
	}
	
	public static String dataToString(Object object) {
		return objectToMap(object).toString();
	}

}
