package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.company.hrsystem.commons.utils.LogUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
import com.company.hrsystem.mapper.NotificationMapper;

@Service
public class TaskScheduleServiceImpl {
	
	@Autowired
	private NotificationMapper notificationMapper;

	/*
	 * Schedule for remove notification from DB.
	 * Task will be start automatically at last day of the month at midnight. 
	 * More detail at https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-cron-expression
	 */
	@Scheduled(cron = "0 0 0 L * *")
	public void removeNotification() {
		LogUtil.info(MessageUtil.getMessagelangUS("remove.notification.by.schedule"));
		notificationMapper.deleteBySchedule();
	}
	
}
