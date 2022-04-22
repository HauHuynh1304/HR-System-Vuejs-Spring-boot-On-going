package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class PostionModel {

	private Integer positionId;

	private String positionName;

	private Boolean deletedFlag;

	private Date createdAt;

	private Date updatedAt;

}