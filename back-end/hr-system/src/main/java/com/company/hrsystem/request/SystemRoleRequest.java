package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.SystemRoleDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemRoleRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SystemRoleDto systemRole;
	
	private SystemRoleRequest data;

}
