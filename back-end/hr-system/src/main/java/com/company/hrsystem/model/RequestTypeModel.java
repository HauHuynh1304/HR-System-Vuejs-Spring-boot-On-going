package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequestTypeModel {

	private Integer requestTypeId;

	private String requestTypeName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}