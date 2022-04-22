package com.company.hrsystem.request;

import com.company.hrsystem.dto.SystemAccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
	
	private SystemAccountDto account;
	
	private ChangePasswordRequest data;
	
}
