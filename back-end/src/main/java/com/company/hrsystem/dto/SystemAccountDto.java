package com.company.hrsystem.dto;

import java.sql.Timestamp;

import com.company.hrsystem.model.SystemAccountModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SystemAccountDto extends SystemAccountModel {

	private static final long serialVersionUID = 7492830020484800687L;

	public SystemAccountDto(Integer systemAccountId, String systemEmail, String systemPassword, Boolean deletedFlag, Timestamp updatedAt) {
		super(systemAccountId, systemEmail, systemPassword, deletedFlag, updatedAt);
	}

}
