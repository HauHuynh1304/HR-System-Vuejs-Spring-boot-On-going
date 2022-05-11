package com.company.hrsystem.dto;

import java.io.Serializable;
import java.util.Set;

import com.company.hrsystem.model.SystemAccountModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthenAccountDto extends SystemAccountModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<AuthenRoleDto> roles;

}
