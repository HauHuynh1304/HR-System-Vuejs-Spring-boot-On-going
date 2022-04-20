package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RefreshTokenModel {

	private Integer refreshTokenId;

	private Integer systemAccountId;

	private String refreshTokenName;

	private String expiryDate;

	public RefreshTokenModel(Integer systemAccountId, String refreshTokenName, String expiryDate) {
		this.systemAccountId = systemAccountId;
		this.refreshTokenName = refreshTokenName;
		this.expiryDate = expiryDate;
	}

}