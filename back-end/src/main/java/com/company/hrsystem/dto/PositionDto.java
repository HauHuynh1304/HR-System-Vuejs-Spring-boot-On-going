package com.company.hrsystem.dto;

import java.sql.Date;

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
	
	private static final long serialVersionUID = -7725365259429505110L;

	private Date startDate;
	
	private Date endDate;

}
