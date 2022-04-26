package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ReasonModel {

	private Integer reasonId;

	private String reasonName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}