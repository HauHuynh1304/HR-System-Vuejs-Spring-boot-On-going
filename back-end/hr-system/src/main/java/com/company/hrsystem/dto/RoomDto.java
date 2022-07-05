package com.company.hrsystem.dto;

import com.company.hrsystem.model.RoomModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RoomDto extends RoomModel {
	
	private static final long serialVersionUID = 1L;

	public RoomDto(Integer roomId, String roomName) {
		super(roomId, roomName);
	}

}
