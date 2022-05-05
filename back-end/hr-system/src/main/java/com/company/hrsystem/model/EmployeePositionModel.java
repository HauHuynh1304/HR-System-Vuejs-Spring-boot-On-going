package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EmployeePositionModel {
	
	private Integer employeeId;
	
	private Integer positionId;

	private Boolean deletedFlag;
	
	private String startDate;
	
	private String endDate;

	private String createdAt;

	private String updatedAt;
	
}
