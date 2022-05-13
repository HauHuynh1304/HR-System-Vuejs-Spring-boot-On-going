package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.dto.PositionDto;
import com.company.hrsystem.dto.RoomDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindListEmployeesRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PersonalInfoDto personalInfo;
	
	private EmployeeDto employee;
	
	private RoomDto room;
	
	private PositionDto position;

	private Boolean isFullDocuments;

	private FindListEmployeesRequest data;

}
