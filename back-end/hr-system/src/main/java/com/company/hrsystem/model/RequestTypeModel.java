package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequestTypeModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer requestTypeId;

	private String requestTypeName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}