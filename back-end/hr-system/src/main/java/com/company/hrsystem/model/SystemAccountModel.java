package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SystemAccountModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer systemAccountId;

	private String systemEmail;

	private String systemPassword;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;

	public SystemAccountModel(Integer systemAccountId, String systemEmail, String systemPassword, Boolean deletedFlag,
			String createdAt, String updatedAt) {
		this.systemAccountId = systemAccountId;
		this.systemEmail = systemEmail;
		this.systemPassword = systemPassword;
		this.deletedFlag = deletedFlag;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}