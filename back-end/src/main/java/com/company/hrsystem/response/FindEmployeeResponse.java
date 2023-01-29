package com.company.hrsystem.response;

import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import com.company.hrsystem.dto.EmployeeDocumentDto;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.dto.HistoryActionDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.dto.RoomDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FindEmployeeResponse implements Serializable {

	private static final long serialVersionUID = 4286288649186523184L;

	private String systemEmail;
	
	private PersonalInfoDto personalInfo;
	
	private EmployeeDto employee;
	
	private RoomDto room;
	
	private List<EmployeePositionDto> positions;
	
	private List<EmployeeDocumentDto> documents;
	
	private List<HistoryActionDto> historyActions;
	
}
