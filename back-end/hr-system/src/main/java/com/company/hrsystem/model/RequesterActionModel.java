package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequesterActionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer requesterActionId;

	private Integer requesterId;

	private String actionType;

	private String createdAt;

	private String updatedAt;
	
}
