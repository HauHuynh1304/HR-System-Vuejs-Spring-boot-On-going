package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.company.hrsystem.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public abstract class EmployeePositionModel implements Serializable {
	
	private static final long serialVersionUID = 6698184660479974312L;

	private Integer employeePositionId;
	
	private Integer employeeId;
	
	private Integer positionId;

	private Boolean deletedFlag;
	
	private Date startDate;
	
	private Date endDate;
	
	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;
	
}
