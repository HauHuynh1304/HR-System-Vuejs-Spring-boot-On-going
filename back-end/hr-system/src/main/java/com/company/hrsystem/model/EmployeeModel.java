package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EmployeeModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer employeeId;

	private String employeeProfileId;

	private Integer roomId;

	private Integer personalInfoId;

	private Integer systemAccountId;

	private Boolean deletedFlag;

	private Date employeeStartDate;

	private Date employeeEndDate;

	private String createdAt;

	private String updatedAt;

	public EmployeeModel(Integer employeeId, String employeeProfileId, Integer roomId, Integer systemAccountId,
			Date employeeStartDate) {
		this.employeeId = employeeId;
		this.employeeProfileId = employeeProfileId;
		this.roomId = roomId;
		this.systemAccountId = systemAccountId;
		this.employeeStartDate = employeeStartDate;
	}
	
}