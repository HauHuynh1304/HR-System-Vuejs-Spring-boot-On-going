package com.company.hrsystem.utils;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ObjectUtil {

	@SuppressWarnings("unchecked")
	public Map<String, Object> objectToMap(Object object) {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}

	public int countNotNullParamater(Object object) {
		Map<String, Object> objToMap = objectToMap(object);
		int countNotNullParamater = 0;
		for (Map.Entry<String, Object> entry : objToMap.entrySet()) {
			if (ObjectUtils.isNotEmpty(entry.getValue())) {
				countNotNullParamater++;
			}
		}
		return countNotNullParamater;
	}
}
