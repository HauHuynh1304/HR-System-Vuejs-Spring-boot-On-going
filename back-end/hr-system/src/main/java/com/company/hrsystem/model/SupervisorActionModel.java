package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SupervisorActionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer supervisorActionId;

	private Integer supervisorId;

	private String actionType;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

	public SupervisorActionModel(Integer supervisorActionId, String actionType, Timestamp updatedAt) {
		this.supervisorActionId = supervisorActionId;
		this.actionType = actionType;
		this.updatedAt = updatedAt;
	}
	
}