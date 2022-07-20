package com.company.hrsystem.dto;

import java.sql.Timestamp;

import com.company.hrsystem.model.SupervisorActionModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SupervisorActionDto extends SupervisorActionModel {

	private static final long serialVersionUID = 1L;

	public SupervisorActionDto(Integer supervisorActionId, String actionType, Timestamp updatedAt) {
		super(supervisorActionId, actionType, updatedAt);
	}

	private String supervisorEmail;

}
