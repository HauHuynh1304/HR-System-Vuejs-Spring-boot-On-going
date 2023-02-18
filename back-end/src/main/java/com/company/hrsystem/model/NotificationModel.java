package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class NotificationModel implements Serializable {

	private static final long serialVersionUID = 4369212301941068342L;

	private Integer notificationId;

	private Integer requestId;

	private Integer senderId;

	private Integer receiverId;

	private String createdAt;

	private Boolean readFlag;

	public NotificationModel(Integer requestId, Integer senderId, Integer receiverId) {
		this.requestId = requestId;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

}
