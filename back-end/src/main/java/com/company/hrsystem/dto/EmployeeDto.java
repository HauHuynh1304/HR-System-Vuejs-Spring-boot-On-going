package com.company.hrsystem.dto;

import java.sql.Date;

import com.company.hrsystem.model.EmployeeModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class EmployeeDto extends EmployeeModel {

	private static final long serialVersionUID = 6840215157600030365L;

	public EmployeeDto(Integer employeeId, String employeeProfileId, Integer roomId, Integer systemAccountId,
			Date employeeStartDate) {
		super(employeeId, employeeProfileId, roomId, systemAccountId, employeeStartDate);
	}

}
