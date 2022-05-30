package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SystemAccountRoleModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer systemAccountRoleId;

	private Integer systemAccountId;

	private Integer systemRoleId;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

}