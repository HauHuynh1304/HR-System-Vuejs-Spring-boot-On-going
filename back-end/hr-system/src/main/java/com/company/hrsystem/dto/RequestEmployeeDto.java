package com.company.hrsystem.dto;

import com.company.hrsystem.model.RequestEmployeeModel;

public class RequestEmployeeDto extends RequestEmployeeModel {

	private static final long serialVersionUID = 1L;

	public RequestEmployeeDto(Integer supervisorActionId, Integer approverActionId, String requestStatus,
			String updatedAt) {
		super(supervisorActionId, approverActionId, requestStatus, updatedAt);
	}

}
