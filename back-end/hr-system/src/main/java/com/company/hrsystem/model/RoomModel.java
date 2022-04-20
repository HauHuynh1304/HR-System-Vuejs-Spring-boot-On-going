package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class RoomModel {

	private Integer roomId;

	private String roomName;

	private Boolean deletedFlag;

	private Date createdAt;

	private Date updatedAt;

}