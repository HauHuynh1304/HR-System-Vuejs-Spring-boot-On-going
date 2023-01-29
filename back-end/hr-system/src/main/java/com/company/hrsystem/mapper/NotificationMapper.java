package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.NotificationDto;
import com.company.hrsystem.request.NotificationRequest;

@Mapper
public interface NotificationMapper {

	int insertNotification(NotificationDto nofitifcation);

	List<NotificationDto> findNotificationByReceiverId(Integer receiverId);

	int markNotificationAsRead(NotificationRequest request);

	int deleteNotificationByReceiver(NotificationRequest request);

	int deleteBySchedule();

}
