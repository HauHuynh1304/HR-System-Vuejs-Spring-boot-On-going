package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ApproverActionModel {

	private Integer approverActionId;

	private Integer approverId;

	private String actionType;

	private String createdAt;

	private String updatedAt;

}