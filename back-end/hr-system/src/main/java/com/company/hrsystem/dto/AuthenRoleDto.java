package com.company.hrsystem.dto;

import java.io.Serializable;

import com.company.hrsystem.model.SystemRoleModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenRoleDto extends SystemRoleModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
}
