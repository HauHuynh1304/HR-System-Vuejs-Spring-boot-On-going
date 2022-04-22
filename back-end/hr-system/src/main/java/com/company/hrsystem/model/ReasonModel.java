package com.company.hrsystem.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
abstract class ReasonModel {

	private Integer reasonId;

	private String reasonName;

	private Boolean deletedFlag;

	private Date createdAt;

	private Date updatedAt;

}