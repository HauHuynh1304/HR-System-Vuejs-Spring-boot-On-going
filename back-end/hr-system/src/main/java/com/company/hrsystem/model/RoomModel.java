package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RoomModel {

	private Integer roomId;

	private String roomName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}