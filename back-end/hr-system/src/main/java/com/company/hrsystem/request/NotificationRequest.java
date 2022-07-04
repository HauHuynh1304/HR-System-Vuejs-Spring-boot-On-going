package com.company.hrsystem.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer[] notificationId;

	private NotificationRequest data;

}
