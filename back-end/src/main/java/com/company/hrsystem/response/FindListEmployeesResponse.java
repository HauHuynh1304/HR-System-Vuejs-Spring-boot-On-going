package com.company.hrsystem.response;

import java.io.Serializable;
import java.util.List;

import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.dto.PositionDto;
import com.company.hrsystem.dto.RoomDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FindListEmployeesResponse implements Serializable {

	private static final long serialVersionUID = -5395091623138106785L;

	private String systemEmail;

	private Boolean isFullDocuments;
	
	private PersonalInfoDto personalInfo;
	
	private EmployeeDto employee;
	
	private RoomDto room;
	
	private List<PositionDto> position;
}
