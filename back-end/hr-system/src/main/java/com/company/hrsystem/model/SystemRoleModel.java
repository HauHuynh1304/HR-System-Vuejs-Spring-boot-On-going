package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SystemRoleModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer systemRoleId;

	private String roleName;

	private String roleDescription;

	private Boolean deletedFlag;

	private String applyScope;

	private String createdAt;

	private Timestamp updatedAt;

	public SystemRoleModel(Integer systemRoleId, String roleName) {
		this.systemRoleId = systemRoleId;
		this.roleName = roleName;
	}
	
}