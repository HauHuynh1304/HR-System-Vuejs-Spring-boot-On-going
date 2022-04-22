package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class SupervisorAcctionModel {

	private Integer supervisorActionId;

	private Integer supervisorId;

	private String actionType;

	private Date createdAt;

	private Date updatedAt;

}