package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SystemRoleModel implements Serializable {

	private static final long serialVersionUID = -3496078142850294830L;

	private Integer systemRoleId;

	private String roleName;

	private String roleDescription;

	private Boolean deletedFlag;

	private String applyScope;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

	public SystemRoleModel(Integer systemRoleId, String roleName) {
		this.systemRoleId = systemRoleId;
		this.roleName = roleName;
	}
	
}