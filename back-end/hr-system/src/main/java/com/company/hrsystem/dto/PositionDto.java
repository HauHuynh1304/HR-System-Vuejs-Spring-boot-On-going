package com.company.hrsystem.dto;

import com.company.hrsystem.model.PositionModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PositionDto extends PositionModel {

	private static final long serialVersionUID = 1L;
	
	private String startDate;
	
	private String endDate;

}
