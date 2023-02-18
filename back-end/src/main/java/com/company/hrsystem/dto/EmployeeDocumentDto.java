package com.company.hrsystem.dto;

import com.company.hrsystem.model.EmployeeDocumentModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class EmployeeDocumentDto extends EmployeeDocumentModel {

	private static final long serialVersionUID = 4006413632061657552L;
	
	private String documentName;

}
