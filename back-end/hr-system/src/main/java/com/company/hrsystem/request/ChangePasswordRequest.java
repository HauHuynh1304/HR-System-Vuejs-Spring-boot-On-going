package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.SystemAccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SystemAccountDto account;
	
	private ChangePasswordRequest data;
	
}
