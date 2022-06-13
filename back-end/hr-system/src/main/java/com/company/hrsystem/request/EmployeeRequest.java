package com.company.hrsystem.request;

import java.io.Serializable;
import java.util.List;

import com.company.hrsystem.dto.EmployeeDocumentDto;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.dto.PersonalInfoDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private EmployeeDto employee;

	private PersonalInfoDto personalInfo;

	private List<EmployeeDocumentDto> documents;

	private List<EmployeePositionDto> positions;

}
