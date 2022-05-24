package com.company.hrsystem.dto;

import com.company.hrsystem.model.RequestEmployeeModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RequestEmployeeDto extends RequestEmployeeModel {

	private static final long serialVersionUID = 1L;

	public RequestEmployeeDto(Integer supervisorActionId, Integer approverActionId, String requestStatus,
			String updatedAt) {
		super(supervisorActionId, approverActionId, requestStatus, updatedAt);
	}

}
