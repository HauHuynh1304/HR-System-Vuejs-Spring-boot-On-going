package com.company.hrsystem.dto;

import com.company.hrsystem.model.NotificationModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class NotificationDto extends NotificationModel {

	private static final long serialVersionUID = 1L;

	public NotificationDto(Integer requestId, Integer senderId, Integer receiverId) {
		super(requestId, senderId, receiverId);
	}

	private String sender;
	
	private String requestType;

}
