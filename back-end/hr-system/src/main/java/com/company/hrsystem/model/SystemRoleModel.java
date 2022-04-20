package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SystemRoleModel {

	private Integer systemRoleId;

	private String roleName;

	private String roleDescription;

	private Boolean deletedFlag;

	private String applyScope;

	private Date createdAt;

	private Date updatedAt;

	public SystemRoleModel(String roleName) {
		this.roleName = roleName;
	}
	
}