package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CacheService {

	@Autowired
	CacheManager cacheManager;

	public void updateCache(String cacheName, String token) {
		cacheManager.getCache(cacheName).put(token, token);
	}

	public void deleteCache(String cacheName, String token) {
		cacheManager.getCache(cacheName).evict(token);
	}

	public boolean isExistsInCache(String cacheName, String token) {
		if (ObjectUtils.isEmpty(cacheManager.getCache(cacheName).get(token))) {
			return false;
		} else {
			return true;
		}
	}
}
