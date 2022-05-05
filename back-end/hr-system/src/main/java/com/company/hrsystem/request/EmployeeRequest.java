package com.company.hrsystem.request;

import java.util.List;

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

	private PersonalInfoDto personalInfo;

	private Integer[] documentIds;

	private List<EmployeePositionDto> positions;

	private EmployeeRequest data;

}
