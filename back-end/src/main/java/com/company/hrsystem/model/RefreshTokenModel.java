package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RefreshTokenModel implements Serializable {

	private static final long serialVersionUID = 8174336802335886036L;

	private Integer refreshTokenId;

	private Integer systemAccountId;

	private String refreshTokenName;

	private String expiryDate;
	
}