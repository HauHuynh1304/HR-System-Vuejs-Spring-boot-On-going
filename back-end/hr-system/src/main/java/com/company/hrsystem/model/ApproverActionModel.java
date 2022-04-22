package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class ApproverActionModel {

	private Integer approverActionId;

	private Integer approverId;

	private String actionType;

	private Date createdAt;

	private Date updatedAt;

}