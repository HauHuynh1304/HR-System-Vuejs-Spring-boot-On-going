package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ReasonModel implements Serializable {

	private static final long serialVersionUID = -4610005234452393570L;

	private Integer reasonId;

	private String reasonName;

	private Boolean deletedFlag;
	
	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

}