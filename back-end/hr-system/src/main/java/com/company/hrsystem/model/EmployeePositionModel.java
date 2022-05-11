package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EmployeePositionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	
	private Integer positionId;

	private Boolean deletedFlag;
	
	private String startDate;
	
	private String endDate;

	private String createdAt;

	private String updatedAt;
	
}
