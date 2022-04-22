package com.company.hrsystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResfreshTokenRequest {

	private String refreshTokenName;
	
	private ResfreshTokenRequest data;

}
