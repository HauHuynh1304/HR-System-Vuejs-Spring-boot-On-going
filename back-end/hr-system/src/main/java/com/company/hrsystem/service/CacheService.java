package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CacheService {

	@Autowired
	CacheManager cacheManager;

	public void updateCache(String cacheName, String key, String value) {
		cacheManager.getCache(cacheName).put(key, value);
	}

	public void deleteCache(String cacheName, String key) {
		cacheManager.getCache(cacheName).evict(key);
	}

	public boolean isExistsInCache(String cacheName, String key) {
		if (ObjectUtils.isEmpty(cacheManager.getCache(cacheName).get(key))) {
			return false;
		} else {
			return true;
		}
	}
}
