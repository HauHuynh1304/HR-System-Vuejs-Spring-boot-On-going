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
public abstract class ApproverActionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer approverActionId;

	private Integer approverId;

	private String actionType;

	private String createdAt;
	
	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

	public ApproverActionModel(Integer approverActionId, String actionType, Timestamp updatedAt) {
		this.approverActionId = approverActionId;
		this.actionType = actionType;
		this.updatedAt = updatedAt;
	}

}