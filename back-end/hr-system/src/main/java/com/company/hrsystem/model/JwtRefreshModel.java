package com.company.hrsystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshModel {
	
	private Integer systemAccountId;
	
	private String refreshTokenName;
	
	private String expiryDate;
	
}
