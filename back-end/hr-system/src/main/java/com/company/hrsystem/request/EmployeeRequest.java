package com.company.hrsystem.request;

import java.util.List;

import com.company.hrsystem.dto.DocumentDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.model.EmployeeModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest extends EmployeeModel {
	
	private static final long serialVersionUID = 1L;

	private PersonalInfoDto personalInfo;

	private List<DocumentDto> documents;

	private List<EmployeePositionDto> positions;

	private EmployeeRequest data;

}
