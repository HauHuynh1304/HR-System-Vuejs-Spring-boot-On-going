package com.company.hrsystem.dto;

import com.company.hrsystem.model.ApproverActionModel;

public class ApproverActionDto extends ApproverActionModel {

	private static final long serialVersionUID = 1L;

	public ApproverActionDto(Integer approverActionId, String actionType, String updatedAt) {
		super(approverActionId, actionType, updatedAt);
	}

}
