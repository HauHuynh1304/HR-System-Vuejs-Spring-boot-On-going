package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.NotificationDto;
import com.company.hrsystem.mapper.NotificationMapper;
import com.company.hrsystem.request.NotificationRequest;

@MapperImpl
public class NotificationMapperImpl {

	@Autowired
	private NotificationMapper notificationMapper;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public int insertNotification(NotificationDto nofitifcation) {
		return notificationMapper.insertNotification(nofitifcation);
	}
	
	public List<NotificationDto> findNotificationByReceiverId(Integer receiverId){
		return notificationMapper.findNotificationByReceiverId(receiverId);
	}
	
	public int markNotificationAsRead(NotificationRequest request) {
		return notificationMapper.markNotificationAsRead(request);
	}
	
	public int deleteNotificationByReceiver(NotificationRequest request) {
		return notificationMapper.deleteNotificationByReceiver(request);
	}
	
	public int deleteBySchedule() {
		return notificationMapper.deleteBySchedule();
	}
	
}
