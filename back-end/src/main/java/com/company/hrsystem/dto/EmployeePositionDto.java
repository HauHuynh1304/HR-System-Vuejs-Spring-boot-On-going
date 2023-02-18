package com.company.hrsystem.dto;

import com.company.hrsystem.model.EmployeePositionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeePositionDto extends EmployeePositionModel {
	
	private static final long serialVersionUID = -397173623168956383L;
	
	private String positionName;

}
