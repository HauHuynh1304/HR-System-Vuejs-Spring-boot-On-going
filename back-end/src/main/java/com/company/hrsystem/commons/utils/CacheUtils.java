package com.company.hrsystem.commons.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheUtils {
	
	private static CacheUtils instance;
	
	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	@Autowired
	private CacheManager cacheManager;

	public static void updateCache(String cacheName, String key, String value) {
		instance.cacheManager.getCache(cacheName).put(key, value);
	}

	public static void deleteCache(String cacheName, String key) {
		instance.cacheManager.getCache(cacheName).evict(key);
	}

	public static Boolean isExistsStringInCache(String cacheName, String key, String value) {
		// cacheManager.getCache(cacheName).get(key).get() may be cause
		// NullPointerException
		try {
			return value.equals(instance.cacheManager.getCache(cacheName).get(key).get().toString()) ? true : false;
		} catch (NullPointerException e) {
			return false;
		}
	}

}
