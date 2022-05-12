package com.company.hrsystem.dto;

import com.company.hrsystem.model.SupervisorActionModel;

public class SupervisorActionDto extends SupervisorActionModel {

	private static final long serialVersionUID = 1L;

	public SupervisorActionDto(Integer supervisorActionId, String actionType, String updatedAt) {
		super(supervisorActionId, actionType, updatedAt);
	}

}
