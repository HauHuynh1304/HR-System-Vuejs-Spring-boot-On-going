package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class RequestEmployeeModel {

	private Integer employeeId;

	private Integer requestId;

	private Integer supervisorActionId;

	private Integer approverActionId;

	private Date startDate;

	private Date endDate;

	private String partialDate;

	private String requestDescription;

	private Date expectedApproveDate;

	private Integer duration;

	private String requestStatus;

	private Date createdAt;

}