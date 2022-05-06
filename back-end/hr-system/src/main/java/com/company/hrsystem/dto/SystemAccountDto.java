package com.company.hrsystem.dto;

import java.io.Serializable;

import com.company.hrsystem.model.SystemAccountModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemAccountDto extends SystemAccountModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public SystemAccountDto(Integer systemAccountId, String systemEmail, String systemPassword, Boolean deletedFlag,
			String createdAt, String updatedAt) {
		super(systemAccountId, systemEmail, systemPassword, deletedFlag, createdAt, updatedAt);
	}

}
