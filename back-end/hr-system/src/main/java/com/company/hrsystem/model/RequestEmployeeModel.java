package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequestEmployeeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer employeeId;

	private Integer requestId;

	private Integer supervisorActionId;

	private Integer approverActionId;
	
	private Integer requesterActionId;

	private Date startDate;

	private Date endDate;

	private String partialDate;

	private String requestDescription;

	private String expectedApproveDate;

	private Double duration;

	private String requestStatus;

	private String createdAt;

	private String updatedAt;

	public RequestEmployeeModel(Integer requesterActionId ,Integer supervisorActionId, Integer approverActionId, String requestStatus,
			String updatedAt) {
		this.requesterActionId = requesterActionId;
		this.supervisorActionId = supervisorActionId;
		this.approverActionId = approverActionId;
		this.requestStatus = requestStatus;
		this.updatedAt = updatedAt;
	}

}