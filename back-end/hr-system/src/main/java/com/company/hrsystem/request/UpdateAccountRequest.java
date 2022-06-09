package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.SystemAccountDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UpdateAccountRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SystemAccountDto account;
	
	private Integer[] addNewRoleIds;
	
	private Integer[] deleteRoleIds;
	
	private UpdateAccountRequest data;

}
