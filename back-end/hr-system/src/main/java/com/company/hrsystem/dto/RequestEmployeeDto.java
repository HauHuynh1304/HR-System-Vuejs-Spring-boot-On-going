package com.company.hrsystem.dto;

import java.sql.Timestamp;

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

	public RequestEmployeeDto(Integer requesterActionId, Integer supervisorActionId, Integer approverActionId,
			String requestStatus, Timestamp updatedAt) {
		super(requesterActionId, supervisorActionId, approverActionId, requestStatus, updatedAt);
	}

	private Integer requestId;

	private String requestType;

	private String reason;

}
