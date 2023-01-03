package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.NotificationDto;
import com.company.hrsystem.request.NotificationRequest;

@Mapper
public interface NotificationMapper {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	int insertNotification(NotificationDto nofitifcation);

	List<NotificationDto> findNotificationByReceiverId(Integer receiverId);

	int markNotificationAsRead(NotificationRequest request);

	int deleteNotificationByReceiver(NotificationRequest request);

	int deleteBySchedule();

}
