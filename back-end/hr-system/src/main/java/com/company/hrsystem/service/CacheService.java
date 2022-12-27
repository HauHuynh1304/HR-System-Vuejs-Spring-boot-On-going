package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.company.hrsystem.serviceInterface.CacheServiceInterface;

@Service
public class CacheService implements CacheServiceInterface {

	@Autowired
	private CacheManager cacheManager;

	public void updateCache(String cacheName, String key, String value) {
		cacheManager.getCache(cacheName).put(key, value);
	}

	public void deleteCache(String cacheName, String key) {
		cacheManager.getCache(cacheName).evict(key);
	}

	public Boolean isExistsStringInCache(String cacheName, String key, String value) {
		// cacheManager.getCache(cacheName).get(key).get() may be cause
		// NullPointerException
		try {
			return value.equals(cacheManager.getCache(cacheName).get(key).get().toString()) ? true : false;
		} catch (NullPointerException e) {
			return false;
		}
	}

}
