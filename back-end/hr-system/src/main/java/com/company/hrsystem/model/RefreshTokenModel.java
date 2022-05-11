package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RefreshTokenModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer refreshTokenId;

	private Integer systemAccountId;

	private String refreshTokenName;

	private String expiryDate;
	
}