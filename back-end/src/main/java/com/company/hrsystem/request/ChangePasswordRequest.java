package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.SystemAccountDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable{
	
	private static final long serialVersionUID = -7705127877008974049L;

	private SystemAccountDto account;
	
	private ChangePasswordRequest data;
	
}
