package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class PostionModel {

	private Integer positionId;

	private String positionName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}