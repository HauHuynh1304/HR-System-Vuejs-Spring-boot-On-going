package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequestEmployeeModel {

	private Integer employeeId;

	private Integer requestId;

	private Integer supervisorActionId;

	private Integer approverActionId;

	private String startDate;

	private String endDate;

	private String partialDate;

	private String requestDescription;

	private String expectedApproveDate;

	private Double duration;

	private String requestStatus;

	private String createdAt;
	
	private String updatedAt;

}