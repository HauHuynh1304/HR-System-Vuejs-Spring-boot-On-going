package com.company.hrsystem.utils;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ObjectUtil {

	@SuppressWarnings("unchecked")
	public Map<String, Object> objectToMap(Object object) {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}
}
