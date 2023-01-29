package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SupervisorActionModel implements Serializable {

	private static final long serialVersionUID = -4478957430505977140L;

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