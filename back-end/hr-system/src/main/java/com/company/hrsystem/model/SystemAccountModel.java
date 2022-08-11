package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

	public SystemAccountModel(Integer systemAccountId, String systemEmail, String systemPassword, Boolean deletedFlag, Timestamp updatedAt) {
		this.systemAccountId = systemAccountId;
		this.systemEmail = systemEmail;
		this.systemPassword = systemPassword;
		this.deletedFlag = deletedFlag;
		this.updatedAt = updatedAt;
	}

}