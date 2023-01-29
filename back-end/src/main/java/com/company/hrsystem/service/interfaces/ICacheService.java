package com.company.hrsystem.service.interfaces;

public interface ICacheService {

	public void updateCache(String cacheName, String key, String value);

	public void deleteCache(String cacheName, String key);

	public Boolean isExistsStringInCache(String cacheName, String key, String value);

}
