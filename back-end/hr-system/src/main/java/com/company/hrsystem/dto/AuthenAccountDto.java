package com.company.hrsystem.dto;

import java.util.Set;

import com.company.hrsystem.model.SystemAccountModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AuthenAccountDto extends SystemAccountModel {

	private static final long serialVersionUID = 5636900598946715134L;
	
	private Set<AuthenRoleDto> roles;

}
