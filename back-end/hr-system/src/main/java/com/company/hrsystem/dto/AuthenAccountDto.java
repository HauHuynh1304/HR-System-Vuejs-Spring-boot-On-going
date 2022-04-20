package com.company.hrsystem.dto;

import java.util.List;

import com.company.hrsystem.model.SystemAccountModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthenAccountDto extends SystemAccountModel {

	private List<AuthenRoleDto> authenRoleModels;

	public AuthenAccountDto(Integer systemAccountId, String systemEmail, String systemPassword,
			List<AuthenRoleDto> authenRoleModels) {
		super(systemAccountId, systemEmail, systemPassword);
		this.authenRoleModels = authenRoleModels;
	}

}
