package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SupervisorActionModel {

	private Integer supervisorActionId;

	private Integer supervisorId;

	private String actionType;

	private String createdAt;

	private String updatedAt;

}