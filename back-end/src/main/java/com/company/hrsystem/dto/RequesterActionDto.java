package com.company.hrsystem.dto;

import com.company.hrsystem.model.RequesterActionModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RequesterActionDto extends RequesterActionModel {
	
	private static final long serialVersionUID = 6078701841083159381L;
	
	private String requesterEmail;
	
}
