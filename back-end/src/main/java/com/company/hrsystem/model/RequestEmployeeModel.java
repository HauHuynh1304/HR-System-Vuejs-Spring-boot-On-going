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
public abstract class RequestEmployeeModel implements Serializable {
	
	private static final long serialVersionUID = 7228540979395164494L;

	private Integer employeeId;

	private Integer requestId;

	private Integer supervisorActionId;

	private Integer approverActionId;
	
	private Integer requesterActionId;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp startDate;
	
	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp endDate;

	private String partialDate;

	private String requestDescription;
	
	@JsonFormat(pattern=DateUtil.DAY_HOUR_SECOND, timezone=DateUtil.TIME_ZONE)
	private Timestamp expectedApproveDate;

	private Double duration;

	private String requestStatus;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

	public RequestEmployeeModel(Integer requesterActionId ,Integer supervisorActionId, Integer approverActionId, String requestStatus,
			Timestamp updatedAt) {
		this.requesterActionId = requesterActionId;
		this.supervisorActionId = supervisorActionId;
		this.approverActionId = approverActionId;
		this.requestStatus = requestStatus;
		this.updatedAt = updatedAt;
	}

}