package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EmployeeDocumentModel implements Serializable {
	
	private static final long serialVersionUID = 5479699783493539718L;

	private Integer employeeDocumentId;

	private Integer employeeId;

	private Integer documentId;

	private Boolean deletedFlag;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

}