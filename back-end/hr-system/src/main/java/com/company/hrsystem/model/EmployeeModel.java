package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class EmployeeModel {

	private Integer employeeId;

	private String employeeProfileId;

	private Integer roomId;

	private Integer personalInfoId;

	private Integer systemAccountId;

	private Boolean deletedFlag;

	private Date employeeStartDate;

	private Date employeeEndDate;

	private Date createdAt;

	private Date updatedAt;

}